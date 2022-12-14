# ORM
*ORM을 사용하면 SQL 반복 작업에서 벗어나, 복잡한 비즈니스 로직에 더 신경 쓸 수있다.*

- `QuerySet` 은 1개의 `Query` 와 0 ~ N 개의 `QuerySet` 으로 이루어져 있다.
- 수행하고자하는 SQL 보다 가져오고자 하는 데이터 리스트를 먼저 떠올리자.
    - 테이블 연관성에는 `Q(), Prefetch(), F()` 가 주로 사용 된다.
- `QuerySet` 이 제공하는 SQL 구조를 벗어난다면, `RawQuerySet` 을 사용한다.
- NativeSQL 사용을 망설이지 말자.
    - SQL 성능이 중요한 경우라면 가끔씩은 ORM으로 원하는 쿼리 결과를 얻을 수 없을 때도 존재한다.
    - 가독성을 위해 사용하는 것이 좋을 수도 있다.
    - 단조로운 SQL 작업을 줄일 수 있으며, Object와 Relational 을 Mapping 해 준다는 ORM의 장점을 얻을 수 없다면 NativeSQL을 사용하자.

<br>

- `Model` 을  `annotate`, `select_related()`, `filter` 그리고 `prefetch_related()` 순서로 `QuerySet` 을 작성하는 것이 좋다.(이 순서가 실제로 발생하는 SQL의 발생 순서와 가장 유사하다.)
    - `prefetch_related()` 가 `filter()` 앞에 있는 것은 피하도록 하자.

<br>

## **`QuerySet` vs `SQL`**
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

***`select_related` 또는 `prefetch_related` 없는 `QuerySet` 에서 `Join` 또는 +1 `Query`(추가 쿼리)가 발생했다면 명시적으로라도 `select_related` 와 `prefetch_related` 를 붙여주는 것이 좋다.***
- 제 3자가 소스코드를 읽었을 때, "해당 필드는 JOIN을 해서 가져왔구나" 혹은 "추가쿼리로 조회했구나" 등의 정보를 좀더 명확하게 알 수 있기 때문이다.


<br><hr><br>

## **`prefetch_related()`를 사용했으나, `QuerySet` 이 `JOIN` 으로 데이터를 조회하는 경우**
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

## **`Prefetch()`: +1 Query에 조건 걸기**
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

## **`FilteredRelation()`: JOIN ON 절에 조건 걸기**
*INNER JOIN의 경우 큰 차이가 없지만 OUTER JOIN의 경우 JOIN ON 절에 조건을 걸어주는 것과 WHERE 절에 조건을 걸어주는 것에는 성능 차이를 보일 수 있다.*

- ON 절은 JOIN 하면서 조건절을 체크하지만, WHERE 절은 JOIN 결과를 완성 시킨 후에 조절을 체크한다.
- ON 절에 조건을 주고 싶다면, FIlteredRelation 을 사용한다.

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

## **`RawQuerySet`**
*Django의 raw() 메소드는 RawQuerySet 을 반환하는데 이는 완전 NativeSQL 이 아니다.*
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
*QuerySet 의 반환타입에는 ModelIterable, ValuesIterable, ValueListIterable, FlatValuesListIterable, NamedValuesListIterable 가 존재한다.*
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

<br><hr><br>

## **`Q()`**
*Q 객체는 SQL 쿼리에서의 WHERE 절에 해당하는 기능을 수행 할 수 있다.*
- `Q()` 객체를 이용해 `OR`, `AND`, `NOT` 연산을 수행 할 수 있다.

<br>

>**`OR` 연산**
```python
>>> from django.db.models import Q
>>> queryset = User.objects.filter(
    Q(first_name__startswith='R') | Q(last_name__startswith='D')
)
>>> queryset
<QuerySet [<User: Ricky>, <User: Ritesh>, <User: Radha>, <User: Raghu>, <User: rishab>]>
```

<br>

>**`AND` 연산**
```python
>>> queryset = User.objects.filter(
    Q(first_name__startswith='R') & Q(last_name__startswith='D')
)
>>> queryset
<QuerySet [<User: Ricky>, <User: Ritesh>, <User: rishab>]>
```

<br><br>

>**예제: 이름(first_name)이 ‘R’로 시작하되, 성(last_name)에 ‘Z’가 포함되지 않은 사용자를 모두 구하라**
```python
>>> queryset = User.objects.filter(
    Q(first_name__startswith='R') & ~Q(last_name__startswith='Z')
 )
```

>**에제로 실행되는 SQL 쿼리**
```sql
SELECT "auth_user"."id",
       "auth_user"."password",
       "auth_user"."last_login",
       "auth_user"."is_superuser",
       "auth_user"."username",
       "auth_user"."first_name",
       "auth_user"."last_name",
       "auth_user"."email",
       "auth_user"."is_staff",
       "auth_user"."is_active",
       "auth_user"."date_joined"
FROM "auth_user"
WHERE ("auth_user"."first_name"::text LIKE R%
       AND NOT ("auth_user"."last_name"::text LIKE Z%))
```
<br>

> **lookup filter(혹은 exclude)**

*필드 별 구체적인 같에 대한 비교를 가능하게 하는 Django 의 내장 모듈*

- `__contains`: 특정 문자가 포함된 것을 찾을 때 사용(**대소문자 구분**)
    ```python
    # english_name 컬럼에 'Blend'이라는 단어가 들어간 데이터 조회 (단, 대소문자 구분)
    In : Drink.objects.filter(english_name__contains="blend")
    Out: <QuerySet []>

    In :  Drink.objects.filter(english_name__contains="Blend")
    Out: <QuerySet [<Drink: 망고 패션 후르츠 블렌디드>, <Drink: 딸기 요거트 블렌디드>]>
    ```

    <br>

- `__icontains`: 특정 문자가 포함된 것을 찾을 때 사용(**대소문자 구분 X**)
    ```python
    # english_name 컬럼에 'blend'이라는 단어가 들어간 데이터 조회 (대소문자를 구분하지 않음)
    In : Drink.objects.filter(english_name__icontains="blend")
    Out: <QuerySet [<Drink: 망고 패션 후르츠 블렌디드>, <Drink: 딸기 요거트 블렌디드>]>

    In : Drink.objects.filter(english_name__icontains="Blend")
    Out: <QuerySet [<Drink: 망고 패션 후르츠 블렌디드>, <Drink: 딸기 요거트 블렌디드>]>
    ```
    <br>


- `__startswith`: 특정 문자로 시작하는 것을 찾을 경우 사용(**대소문자 구분**)
    ```python
    # english_name 컬럼에 'Nitro'로 시작하는 문자열을 가진 데이터 조회 (단, 대소문자 구분) 
    In : Drink.objects.filter(english_name__startswith="Nitro")
    Out: <QuerySet [<Drink: 나이트로 바닐라 크림>, <Drink: 나이트로 쇼콜라 클라우드>]>

    In : Drink.objects.filter(english_name__startswith="nitro")
    Out: <QuerySet []>
    ```

    <br>

- `__endswith`: 특정 문자로 끝나는 것을 찾을 때 사용(**대소문자 구분**)
    ```python
    # english_name 컬럼에서 'Tea'로 끝나는 문자를 가진 데이터 조회 (대소문자 구분)
    In : Drink.objects.filter(english_name__endswith="Tea")
    Out: <QuerySet [<Drink: 라임패션티>]>

    In : Drink.objects.filter(english_name__endswith="tea")
    Out: <QuerySet []>
    ```

    <br>

- `__gt`: 특정 값보다 큰 데이터 값을 조회(gt = greater than)
    ```python
    # id가 3보다 큰 데이터만 조회(3포함 X)
    In : Drink.objects.filter(id__gt=3)
    Out: <QuerySet [<Drink: 딸기 요거트 블렌디드>, <Drink: 블랙 티 레모네이드>, <Drink: 쿨라임 피지오>, <Drink: 말차 초콜릿 라떼>, <Drink: 라임패션티>]>
    ```
    <br>

- `__lt`: 특정 값보다 작은 데이터 값을 조회(lt = less than)
    ```python
    # id가 3보다 작은 데이터만 조회
    In : Drink.objects.filter(id__lt=3)
    Out: <QuerySet [<Drink: 나이트로 바닐라 크림>, <Drink: 나이트로 쇼콜라 클라우드>]>
    ```

    <br>

- `__isnull`: True 로 지정 시 특정 필드 값이 null 인 것만 조회
    ```python
    # description 컬럼이 null인 것만 조회
    In : Drink.objects.filter(description__isnull=True)
    Out: <QuerySet [<Drink: 나이트로 바닐라 크림>, <Drink: 나이트로 콜드 브루>]>

    # description 컬럼이 null이 아닌 것만 조회
    In : Drink.objects.filter(description__isnull=False)
    Out: <QuerySet [<Drink: 망바>, <Drink: 딸요>, <Drink: 말차라떼>, <Drink: 얼그레이>]>
    ```

    <br>

- `__in`: 리스트 안에 지정한 문자열들 중에 하나라도 포함된 데이터를 찾을 때 사용한다.(**단, 문자열과 정확히 일치해야한다.**)
    ```python
    # english_name 필드에 'Malcha' 또는 'Nitro Cold Brew' 값이 있는 것만 조회
    In : Drink.objects.filter(english_name__in=['Malcha', 'Nitro Cold Brew'])
    Out: <QuerySet [<Drink: 나이트로 쇼콜라 클라우드>, <Drink: 말차 초콜릿 라떼>]>


    # english_name 필드에 'Malcha' 또는 'Nitro' 값이 있는 것만 조회
    In : Drink.objects.filter(english_name__in=['Malcha', 'Nitro'])
    Out: <QuerySet [<Drink: 말차 초콜릿 라떼>]>
    ```

    <br>

- `__year`, `__month`, `__day`, `__date`: `date` 타입의 필드에서 특정 년(`__year`), 월(`__month`), 일(`__day`) 혹은 특정 날짜((__date : YY-MM-DD 형식)의 데이터를 찾을 때 사용한다.

    ```python
    # pub_date 필드에서 년도가 2021 인 것만 조회
    In : Question.objects.filter(pub_date__year='2021')
    Out: <QuerySet [<Question: What's new?>, <Question: new>]>
    ```

<br><hr><br>

## **`F()`**

*F() 객체는 모델의 필드 혹은 어노테이트된 열의 값을 나타낸다. 실제로 데이터베이스에서 python 메모리로 가져오지 않고, 모델 필드 값을 참조하고 이를 데이터베이스에서 작업 할 수 있다.*

- python 이 아닌 데이터베이스에서 연산을 처리한다.
- 몇몇 작업에 필요한 쿼리 수를 줄일 수 있다.
>**`F()` 사용 전**
```python
reporter = Reporters.objects.get(name='Tintin')
reporter.stories_filed += 1
reporter.save()
```
1. `get()` 매서드로 객체를 얻어온다.
2. 객체의 `stories_filed` 필드 값을 `python` 메모리에 가져온다.
3. **`python` 연산자를 이용해 값을 1 증가** 시킨다. 
4. `save()` 메서드를 사용하여 DB에 저장한다.

*많은 양의 객체를 얻어온 후, 모든 객체에 대한 값 변경이 필요하다면 각 객체마다 
**DB에서 값 얻기 → Python 메모리에 저장 후 변경 → DB에 다시 저장** 의 작업이 반복된다.*

<br>

>**`F()` 사용 후**
```python
Reporters.objects.filter(name='Tintin').update(stories_filed=F('stories_filed') + 1)
```
1. `get()` 메서드로 객체를 얻어온다.
2. 객체의 `stories_filed` 값을 `F('stories_filed') + 1)` 를 저장한다.
3. 변경된 `stories_filed` 값을 `DB` 에 반영한다.
*2 라는 값을 직접 저장하는 것이 아닌, **현재의 값에서 +1 된 값을 저장하는 SQL 구문**이다.(save() 메서드 사용 시 해당 구문이 실행된다.)*

<br>

- F() 객체를 사용하면 경쟁조건(Race condition)을 피할 수 있다.
    - 어노테이션, 필터링, 정렬 등에 굉장히 유용하다.
    >**정렬에 사용되는 경우**
    ```python
    from django.db.models import F
    Company.object.order_by(F('last_contacted').desc(nulls_last=True))
    ```

    <br>

    >**어노테이션에 사용되는 경우**
    ```python
    company = Company.objects.annotate(
    chairs_needed=F('num_employees') - F('num_chairs'))
    ```
 <br>

>**주의사항**
```python
from django.db.models import F
reporter = Reporters.objects.get(name='Tintin')
reporter.stories_filed = F('stories_filed') + 1
reporter.save()
```
- `stories_filed` 의 증가된 값을 확인하기 위해서는 다시 값을 불러와야한다.
- `F()` 객체를 사용하여 값을 변경하였을 때, 값이 직접 대입되는 것이 아닌 연산에 맞는 SQL 구문을 적용시키는 것이다.
- `F()` 객체는 모델의 인스턴스를 저장한 후에도 값이 유지되기 때문에, 추가로 **`save()`를 진행하면 SQL 구문이 또 적용**된다.
    - 이 때는 `refresh_from_db()` 를 사용하여 DB에서 값을 로드하여 이러한 지속성을 막는다.
    >**`refresh_from_db()`**
    ```python
    from django.db.models import F

    reporter = Reporters.objects.get(name='Tintin')
    reporter.stories_filed = F('stories_filed') + 1
    reporter.save()

    repoter.refresh_from_db()
    ```

<br><hr><br>

## **`[CLASS NAME].object.prefetch_related()`**

*prefetch_related()는 새로운 QuerySet을 호출한다.*
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

<br><hr><br>


## **`[CLASS NAME].objects.count()`**
*QuerySet에 포함된 데이터 개수를 리턴한다.*
```python
# Drink 테이블에 몇 개의 데이터가 들어있는지 조회
In : Drink.objects.count()

Out: 8
```

<br><hr><br>

## **`[CLASS NAME].objects.exists()`**
*해당 테이블에 데이터가 들어 있는지 확인한다. 있을경우 `True`, 없을경우 `False` 를 반환한다.*
```python
# Menu에 name이 '음료'인 데이터가 있으면 True, 없으면 False
In : Menu.objects.filter(name="음료").exists()

Out: True
```

## **`[CLASS NAME].objects.values()`**
*QuerySet의 내용을 딕셔너리 형태로 반환한다. 인자값에 아무 것도 넣지 않으면 해당 클래스의 모든 필드와 그 값을 보여주고, 인자값에 특정 필드를 입력하면 입력한 필드에 대한 값을 반환한다.*
```python
# Menu 테이블의 모든 필드를 딕셔너리 형태로 반환
In : Menu.objects.values()

Out: <QuerySet [{'id': 1, 'name': '음료'}, {'id': 2, 'name': '푸드'}, {'id': 3, 'name': '상품'}, {'id': 4, 'name': '카드'}]>


# Menu 테이블의 name 필드만 딕셔너리 형태로 반환
In : Menu.objects.values('name')

Out: <QuerySet [{'name': '음료'}, {'name': '푸드'}, {'name': '상품'}, {'name': '카드'}]>
```

<br><hr><br>


## **`[CLASS NAME].objects.valuse_list()`**
*values()와 같으나 QuerySet의 내용을 딕셔너리가 아닌 튜플 타입으로 반환한다.*
```python
In : Menu.objects.values_list()

Out: <QuerySet [(1, '음료'), (2, '푸드'), (3, '상품'), (4, '카드')]>


In : Menu.objects.values_list('name')

Out: <QuerySet [('음료',), ('푸드',), ('상품',), ('카드',)]>
```

<br><hr><br>

## **`[CLASS NAME].objects.order_by()`**
*특점 필드를 기준으로 정렬을 할 때 사용. 필드명 앞에 -가 붙으면 내림차순을 의미한다.*
```python
# korean_name 필드를 기준으로 오름차순 정렬
In : Drink.objects.order_by('korean_name')

Out: <QuerySet [<Drink: 나이트로 바닐라 크림>, <Drink: 나이트로 쇼콜라 클라우드>, <Drink: 딸기 요거트 블렌디드>, <Drink: 라임패션티>, <Drink: 말차 초콜릿 라떼>, <Drink: 망고 패션 후르츠 블렌디드>, <Drink: 블랙 티 레모네이드>, <Drink: 쿨라임 피지오>]>


# korean_name 필드를 기준으로 내림차순 정렬, 두번째 기준은 id 필드
In : Drink.objects.order_by('-korean_name', 'id')

Out: <QuerySet [<Drink: 쿨라임 피지오>, <Drink: 블랙 티 레모네이드>, <Drink: 망고 패션 후르츠 블렌디드>, <Drink: 말차 초콜릿 라떼>, <Drink: 라임패션티>, <Drink: 딸기 요거트 블렌디드>, <Drink: 나이트로 쇼콜라 클라우드>, <Drink: 나이트로 바닐라 크림>]>
```


<br><hr><br>

## **`[CLASS NAME].objects.first() // [CLASS NAME].objects.last()`**
*QuerySet 결과 중 가장 첫번째, 혹은 가장 마지막 row만을 조회할 때 사용하며 둘 다 객체 다입으로 반환한다.*
```python
# 전체 조회
In : Drink.objects.all()

Out: <QuerySet [<Drink: 나이트로 바닐라 크림>, <Drink: 나이트로 쇼콜라 클라우드>, <Drink: 망고 패션 후르츠 블렌디드>, <Drink: 딸기 요거트 블렌디드>, <Drink: 블랙 티 레모네이드>, <Drink: 쿨라임 피지오>, <Drink: 말차 초콜릿 라떼>, <Drink: 라임패션티>]>


# 가장 첫번째 row만 조회
In : Drink.objects.first()

Out: <Drink: 나이트로 바닐라 크림>


# 가장 마지막 row만 조회
In : Drink.objects.last()

Out: <Drink: 라임패션티>
```

<br><hr><br>

## **`[CLASS NAME].objects.aggregate`**
*django의 집계함수 모듈(Avg, Max, Min, Count, Sum 등)을 사용할 때 사용하는 메소드. 집계함수들을 파라미터로 받는다. 딕셔너리 타입으로 반환한다.*
```python
# 집계함수를 사용하려면 import 해줘야 함
In : from django.db.models import Max, Min, Avg, Sum

# Nutrition 테이블의 id 컬럼과 one_serving_kcal 컬럼만 조회
In : Nutrition.objects.values('id','one_serving_kcal')

Out: <QuerySet [{'id': 1, 'one_serving_kcal': Decimal('75.00')}, {'id': 2, 'one_serving_kcal': Decimal('120.00')}]>


# one_serving_kcal 값 모두 더하기
In : Nutrition.objects.aggregate(Sum('one_serving_kcal'))

Out: {'one_serving_kcal__sum': Decimal('195.00')}


# one_serving_kcal컬럼에서 가장 큰 값과 가장 작은 값의 차이
In : Nutrition.objects.aggregate(diff_kcal = Max('one_serving_kcal') - Min('one_serving_kcal'))

Out: {'diff_kcal': Decimal('45.00')}


# one_serving_kcal 컬럼 값들의 평균
In : Nutrition.objects.aggregate(avg_kcal = Avg('one_serving_kcal'))

Out: {'avg_kcal': Decimal('97.500000')}
```



<br><hr><br>

## **`[CLASS NAME].objects.annotate()`**
*`annotate()` 는 칼럼을 정의하여 준다. 또한 집계함수를 사용하여 반환할 수 있으며, SQL의 `group by` 절과 같은 의미라고 생각할 수 있다. 결과는 `QuerySet` 형태로 반환한다.*
- Django 에서 이미 존재하는 필드의 이름과 동일한 이름으로 `annotate()` 할 수 없음을 주의한다.
<br>

>Django
```python
In : Nutrition.objects.values('drink_id__category_id').annotate(Sum('one_serving_kcal'))

Out: <QuerySet [{'drink_id__category_id': 1, 'one_serving_kcal__sum': Decimal('80.00')}, {'drink_id__category_id': 2, 'one_serving_kcal__sum': Decimal('410.00')}, {'drink_id__category_id': 3, 'one_serving_kcal__sum': Decimal('170.00')}, {'drink_id__category_id': 4, 'one_serving_kcal__sum': Decimal('425.00')}]>
```

<br>

>SQL
```sql
select d.category_id, sum(n.one_serving_kcal)
  from nutritions n, drinks d 
 where n.drink_id = d.id 
 group by d.category_id;
```

<br><hr><br>

## **SubQuery**
<br>

>**WHERE 절의 SubQuery**
```python
# Django
item = Item.objects.all()
base = Base.objects.filter(no__in=Subquery(item.values('no')))

# SQL
SELECT *
FROM base
WHERE no IN (SELECT no FROM item)
```

<br>

>**SELECT 절의 SubQuery**
```python
# Django
item = Item.objects.all()
base = Base.objects.annotate(no=Subquery(item.values('no')))

# SQL
SELECT *, (SELECT no FROM item) AS "no"
FROM base