# ORM
*ORM을 사용하면 SQL 반복 작업에서 벗어나, 복잡한 비즈니스 로직에 더 신경 쓸 수있다.*

- **`prefetch_related()`**

    *`prefetch_related()`는 새로운 QuerySet을 호출한다.*
    - `Prefetch()` 메서드로 따로 querset을 지정해주지 않으면 `역참조모델.objects.all()` 이 기본 생성된다.

    > 1번 로직
    ```python
    .prefetch_related(
    '역방향참조_필드1',
    '역방향참조_필드2',
    )
    ```

    > 2번 로직
    ```python
    .prefetch_related(
    Prefetch('역방향참조_필드1',queryset=역방향참조_모델1.objects.all()),
    Prefetch('역방향참조_필드1',queryset=역방향참조_모델2.objects.all()),
    )
    ```
    *1번로직과 2번로직은 동일하다.* 
    
    <br>

    > `Prefetch()` 에 선언된 queryset들은 새로운 QuerySet이므로 자유롭게 작성이 가능하다.
    ```python
    .prefetch_related(
    Prefetch('역방향참조_필드1',
            queryset=역방향참조_모델1.objects
                     .select_related('역방향참조_모델1의_정방향참조_필드')
                     .prefetch_related('역방향참조_모델1의_역방향참조_필드')
                     .annotate(커스텀필드_블라블라=~~~~~~)
                     .filter(조건절_블라블라~~~)                   
     ),
  
    )
    ```



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
 
## **명시하지 않아도 발생하는 Query**
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


<br><hr><br>

## **Prefetch(): +1 Query에 조건 걸기**
>MySQL
- 위에서의 **prefetch_related()를 사용했으나, QuerySet이 JOIN으로 데이터를 조회하는 경우** 에서 의도한 쿼리는 아래와 같다.
```sql
SELECT `orm_practice_app_company`.`id`,
        `orm_practice_app_company`.`name`,
        `orm_practice_app_company`.`tel_num`,
        `orm_practice_app_company`.`address`
  FROM `orm_practice_app_company`
  WHERE `orm_practice_app_company`.`name` = 'company_name1';
      

SELECT "orm_practice_app_product"."id", "orm_practice_app_product"."name", "orm_practice_app_product"."price", "orm_practice_app_product"."product_owned_company_id"
    FROM "orm_practice_app_product"
WHERE "orm_practice_app_product"."product_owned_company_id" IN (1) 
     -- 이런식으로 조건절이 붙기를 기대함 >>> -- AND name = 'product_name3'; 
```

- `prefectch_related()`로 추가되는 쿼리에 조건을 걸기 위해서는 `Prefetch()` 문법을 사용하여 `QuerySet` 을 작성해야한다.
>Django
```python
 Company.objects
         .prefetch_related(
                   Prefetch('product_set', queryset=Product.objects.filter(product__name='product_name3')))
         .filter(name='company_name1')
 
```

<br><hr><br>

## **FilterdRelation(): JOIN ON 절에 조건 걸기**
*INNER JOIN의 경우 큰 차이가 없지만 OUTER JOIN의 경우 JOIN ON 절에 조건을 걸어주는 것과 WHERE 절에 조건을 걸어주는 것에는 성능 차이를 보일 수 있다.*

- ON 절은 JOIN 하면서 조건절을 체크하지만, WHERE 절은 JOIN 결과를 완성 시킨 후에 조절을 체크한다.
- ON 절에 조건을 주고 싶다면, FIlterdRelation 을 사용한다.

>Django
```python
# Join Table의 on 구문에 조건 걸기  여기서 .select_related('this_is_join_table_name')
    (Product.objects
     #.select_related('this_is_join_table_name') 안 붙어도 되지만 쿼리셋 가독성 측면에서 붙는게 더 좋다
     .annotate(this_is_join_table_name=FilteredRelation('product_owned_company',
                                                           condition=Q(product_owned_company__name='company_name34'),
                                                       ),
              )
     .filter(this_is_join_table_name__isnull=False)
    )
```

<br>

>MySQL
```sql
SELECT "orm_practice_app_product"."id", "orm_practice_app_product"."name", "orm_practice_app_product"."price", "orm_practice_app_product"."product_owned_company_id" 
FROM "orm_practice_app_product" 
INNER JOIN "orm_practice_app_company" this_is_join_table_name
    ON ("orm_practice_app_product"."product_owned_company_id" =  this_is_join_table_name."id" 
          AND ( this_is_join_table_name."name" = 'company_name34') # 이 조건을 걸기위해 FilterRelation()을 사용한다
        ) 
WHERE  this_is_join_table_name."id" IS NOT NULL ;
```
- `prefetch_related()` 은 `Prefetch()` 로 `select_related()` 은 `FilterRelation()` 로 조건절을 좀 더 섬세하게 다룰 수 있다.


<br><hr><br>

## **QuerySet 이 INNER, OUTER JOIN 을 선택하는 기준**
*모델을 마이그레이션 할때
f**ield= model.ForeignKey( null = False )** 이면 **Inner Join** 이고 **field= model.ForeignKey( null = True )** 이면 **Left Outer Join**이다.*

- `null=True` 인 외래키 필드를 `INNER JOIN` 하기
    - 이는 할 수 없어야 하지만 `null=True` 인 엔티티를 `QuerySet` 이 `INNER JOIN` 으로 조회한다면 `JOIN` 되는 테이블쪽이 `null` 이면 SQL결과 데이터에서 누락된다.


<br><hr><br>

## **RawQuerySet**
*Django의 `raw()` 메소드는 `RawQuerySet` 을 반환하는데 이는 완전 `NativeSQL` 이 아니다.*
- `RawQuerySet` 과 `QuerySet` 의 차이점은 메인 쿼리를 `NativeSQL` 로 작성한다는 것이다.

>Django
```python
raw_queryset = (User.objects
                .raw('select * from auth_user where id=1')
                .prefetch_related('user_permissions')
)
```
- 추가 쿼리셋인 `.prefetch_related()` 와 `Prefetch()` 사용은 자유롭다.
- RawQuerySet 사용시 주의사항
    - 모델의 `property` 이름들이 반드시 매칭되어야한다.
    - 매칭이 되지 않을 시, 해당 `property` 가 비어버리게 되고, `RawQuerySet` 은 그 값을 찾기위해 다시 쿼리를 호출한다.
    ```python
    raw_queryset = ( User.objects.raw(
               'select id, username as 없는_프로퍼티_명, 
                   from auth_user where id=1',
               )
    list(raw_queryset) # 애트리뷰트와 프로퍼티가 매칭되지 못해서 sql을 두번 호출한다.


    (0.002) select id, username as ddd from auth_user where id=1; 
    (0.002) SELECT `auth_user`.`id`, `auth_user`.`username` # 불필요쿼리호출
                    FROM `auth_user` WHERE `auth_user`.`id` = 1;
    ```


<br>

- **`.raw()` 와 사용이 불가능한 메서드**
    - `.select_related()`: 메인쿼리의 `JOIN` 옵션을 주는 메서드
    - `FilteredRelation()`: `ON` 절 제어 옵션 역시 `JOIN` 이 되지 않아 불가능하다.
    - `.annotate()`: 메인쿼리에 `AS` 옵션을 주는 메서드
    - `.order_by()`: 메인쿼리에 `ORDER BY` 옵션을 주는 메서드
    - `extra()`: 메인쿼리에 `SQL` 을 추가 반영하는 메서드

<br><hr><br>

## **QuerySet의 반환 타입**
*`QuerySet` 의 반환타입에는 `ModelIterable`, `ValuesIterable`, `ValueListIterable`, `FlatValuesListIterable`, `NamedValuesListIterable` 가 존재한다.*
>Django
```python
# ModelIterable
result : List[Model] = Model.objects.all() 
                                    .only() # 지정한 필드만 조회
                                    .defer() # 지정한 필드를 제외하고 조회
# ValuesIterable
result : List[Dict[str,Any]] = Model.objects.valeus()
  
# ValuesListIterable
result : List[Tuple[str,Any]] = Model.objects.values_list()
  
# FlatValuesListIterable  (ValuesListIterable 상속받음)
result : List[Any] = Model.objects.values_list('pk',flat=True)
  
# NamedValuesListIterable  (ValuesListIterable 상속받음)
result : List[Raw] = Model.objects.values_list(named=True)
                      # django에서 제공하는 Raw라는 객체에 데이터를 담아서 리턴
```
- values() 와 values_list() 는 only() 또는 defer()을 통해 대체하는 것이 좋다.
    - ModelIterable 만이 Model의 property와 method 들에 접근 가능하기 때문이다.
    <br>
    
        *`@property` 는 메서드를 필드처럼 사용할 수 있게 해준다.*

    - `values()`, `values_list()` 으로 데이터를 반환 받으려 하면, `QuerySet` 은 `select_related()`, `prefech_related()` 에 옵션을 주더라도 이를 무시한다.
        >Django
        ```python
        # ModelIterable 인 경우 객체 반환 (문제없음)
        qqq = list(User.objects.prefetch_related('user_permissions').filter(id=1))
      
        # 메인쿼리 
        SELECT * FROM `auth_user` WHERE `auth_user`.`id` = 1; 
       
        # 추가 쿼리(셋)
        SELECT * 
        FROM `auth_permission` 
        INNER JOIN `auth_user_user_permissions` ON (`auth_permission`.`id` = `auth_user_user_permissions`.`permission_id`) 
        INNER JOIN `django_content_type` ON (`auth_permission`.`content_type_id` = `django_content_type`.`id`)
        WHERE `auth_user_user_permissions`.`user_id` IN (1) ORDER BY `django_content_type`.`app_label` ASC, `django_content_type`.`model` ASC, `auth_permission`.`codename` ASC; args=(1,)

        # 결과값: [<User: username1>]

        -----------------

        # Values (select_related 무시하고 JOIN 안함 & 특정참조_필드에 해당하는 모델이 아닌 외래키 값 자체를 반환 )
        gg = list(Product.objects.select_related('product_owned_company').filter(id=1).values())


        (0.002) SELECT `orm_practice_app_product`.`id`, `orm_practice_app_product`.`name`,
        `orm_practice_app_product`.`price`, `orm_practice_app_product`.`product_owned_company_id`
        FROM `orm_practice_app_product` 
        WHERE `orm_practice_app_product`.`id` = 1; 

        # .select_related('product_owned_company') 를 무시하고 JOIN 하지 않는다... 
        # 그리고 raw단위로 데이터를 가져와서 product_owned_company객체 대신 foreignKey pk를 가져옴 
        [{'id': 1, 'name': 'product_name1', 'price': 94772, 'product_owned_company_id': 40}]


        ---------------

        # Values ( select_related 된 참조모델의 데이터를 받으려면 'product_owned_company__name' 이렇게 직접 명시해줘야 JOIN 한다)
        gg = list(Product.objects.select_related('product_owned_company').filter(id=1).values('product_owned_company__name'))
        
        
        (0.003) SELECT `orm_practice_app_company`.`name` 
        FROM `orm_practice_app_product` 
        LEFT OUTER JOIN `orm_practice_app_company` ON (`orm_practice_app_product`.`product_owned_company_id` = `orm_practice_app_company`.`id`) 
        WHERE `orm_practice_app_product`.`id` = 1; args=(1,)
        # JOIN 하려면 values() 안에 전부 명시해줘야한다. 또는 annotate()
        # values_list()도 동일하다.
        # 결과값: [{'product_owned_company__name': 'company_name40'}]

        ---------------


        # ValuesList ( .prefetch_related 를 무시하고 그냥 JOIN)
        qqq = list(User.objects.prefetch_related('user_permissions').filter(id=1).values_list('user_permissions'))
        

        # 메인쿼리 (prefetch_related 무시하고 메인쿼리에 JOIN 됨... )
        SELECT `auth_user_user_permissions`.`permission_id` 
        FROM `auth_user` 
        LEFT OUTER JOIN `auth_user_user_permissions` ON (`auth_user`.`id` = `auth_user_user_permissions`.`user_id`) 
        WHERE `auth_user`.`id` = 1; args=(1,)
        
        # 결과값: [(3,), (4,)]


        ---------------


        # NamedValuesList ( .prefetch_related 를 무시하고 그냥 JOIN)
        qqq = list(User.objects.prefetch_related('user_permissions').filter(id=1).values_list('user_permissions', named=True))
        
        # 메인쿼리 ()
        SELECT `auth_user_user_permissions`.`permission_id` 
        FROM `auth_user` 
        LEFT OUTER JOIN `auth_user_user_permissions` ON (`auth_user`.`id` = `auth_user_user_permissions`.`user_id`)
        WHERE `auth_user`.`id` = 1; args=(1,)
        
        # 결과값: [Row(user_permissions=3), Row(user_permissions=4)]
        ```
        - 특정 참조_필드에 해당하는 모델이 아닌 외래키 값 자체를 반환한다.
        - **`values()` 와 `values_list()` 를 붙이는 것 만으로도 발생하는 쿼리가 변할 수 있다.**
            - `model` 단위로 데이터를 변환하는 것이 아니라 `row` 단위로 데이터를 반환한다.
            - `join` 된 값들을 가져오려면 전부 값을 선언해줘야 한다.
            - 모델이 아니라 `property` 들에 접근이 안되서 불편하다.