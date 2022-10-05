# ORM
*ORM을 사용하면 SQL 반복 작업에서 벗어나, 복잡한 비즈니스 로직에 더 신경 쓸 수있다.*

> Django
```python
```

>MySQL
```sql
```


## **QuerySet vs SQL**
> Django
```python
(Model.objects
    .filter(조건절)
    .select_related('정방향_참조_필드')   # 해당 필드를 join해서 가져온다.
    .prefetch_related('역방향_참조_필드') # 해당 필드는 추가쿼리로 가져온다.
)
```
<br>

> MySQL
```sql
select * from 'Model' m
(inner OR left outer) join '정방향_참조_필드' r on m.r_id=r.id 
'where '조건절';

select * from '역방향_참조_필드' where id in ('첫번째 쿼리 결과의 id 리스트');
```

<br>

- `select_related()` 는 `Join` 을 의도하고
- `prefetch_related()` 는 `+1 Query` 를 의도하고 사용한다.
- 하지만 `QuerySet` 에 추가적으로 준 옵션들은 `QuerySet` 이 불필요하다고 판단하면, `QuerySet` 은 개발자의 의도를 무시하고 `Query` 를 작성한다.

*100% 이렇게 매칭되지는 않는다.*


<br><hr><br>

## 명시하지 않아도 발생하는 Query
> Django
```python
(OrderedProduct.objects
 .filter(id=1, related_order__descriptions='sdfsdf')
 #.select_related('related_order'))
```

>MySQL
```sql
SELECT *
    FROM "orm_practice_app_orderedproduct" 
    INNER JOIN "orm_practice_app_order" 
        ON ("orm_practice_app_orderedproduct"."related_order_id" = "orm_practice_app_order"."id")
    WHERE ("orm_practice_app_orderedproduct"."id" = 1 AND "orm_practice_app_order"."descriptions" = '주문의 상세내용입니다...1')
"""

```
- `select_related` 를 사용하지 않더라도 필요하다 판단되면 `JOIN` 한다.
- 하지만 이 경우에도 `.selet_related('related_order')`을 같이 붙여주는 것이 좋다.

<br>

***select_related또는 prefetch_related 없는 QuerySet에서 Join 또는 +1 Query(추가 쿼리)가 발생했다면 명시적으로라도 select_related와 prefetch_related를 붙여주는 것이 좋다.***
- 제 3자가 소스코드를 읽었을 때, "해당 필드는 JOIN을 해서 가져왔구나" 혹은 "추가쿼리로 조회했구나" 등의 정보를 좀더 명확하게 알 수 있기 때문이다.


<br><hr><br>

## **prefetch_related()를 사용했으나, QuerySet이 JOIN으로 데이터를 조회하는 경우**
>Django
```python
company_queryset: QuerySet = Company.objects.prefetch_related('product_set')
                                            .filter(
                                                name='company_name1',
                                                product__name='product_name3'
                                                )

```


<br>

>MySQL
```sql
  SELECT "orm_practice_app_company"."id", "orm_practice_app_company"."name", 
        "orm_practice_app_company"."tel_num", "orm_practice_app_company"."address" 
      FROM "orm_practice_app_company"
          INNER JOIN "orm_practice_app_product" 
          ON ("orm_practice_app_company"."id" = "orm_practice_app_product"."product_owned_company_id") 
          --여기서 join이 발생했으면 join으로 product row를 다 조회 하는게 맞는데 join을 통해 조건절 검사만하고 --
          -- 두번째 쿼리에서 product를 한번 더 조회한다 --
      WHERE (
        "orm_practice_app_company"."name" = 'company_name1' 
        AND 
        "orm_practice_app_product"."name" = 'product_name3'
        )  
      LIMIT 21;
  

  -- prefetch_related옵션으로 인해 해당 쿼리가 발생 -> 결과적으로 product를 불필요하게 2번 조회함 --
  SELECT "orm_practice_app_product"."id", "orm_practice_app_product"."name", 
        "orm_practice_app_product"."price", "orm_practice_app_product"."product_owned_company_id" 
      FROM "orm_practice_app_product" 
  WHERE "orm_practice_app_product"."product_owned_company_id" IN (1);
```

- 첫 쿼리에서 `Product(orm_practice_app_product)` 를 `JOIN` 했음에도 불구하고 추가 쿼리에서 `Product` 를 조회한다.

*정방향 참조된 모델들도 `prefetch_related()` 를 통해 `JOIN` 이 아닌 추가 쿼리로 가져올 수는 있다.*

- 이런 경우 `JOIN` 으로 해결되는것이 좋은 선택이다.

> Django
```python
# 이 쿼리는 의도한대로 +1개의 쿼리로 related_order를 조회한다 
# filter절에서 related_order에 대해 별다른 내용이 없어서 반항없이 개발자의 의도대로 따라준다.
OrderedProduct.objects.filter(product_cnt=30)
                       .prefetch_related('related_order')
```

<br>

>MySQL
```sql
SELECT * 
    FROM "orm_practice_app_orderedproduct"
     WHERE "orm_practice_app_orderedproduct"."product_cnt" = 30;
    SELECT * 
     FROM "orm_practice_app_order" 
     WHERE "orm_practice_app_order"."id" IN (~~~~~~~);
```