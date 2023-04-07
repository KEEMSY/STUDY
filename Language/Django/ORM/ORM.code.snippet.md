>Django
```python
queryset = (Model.objects
            .select_related('정방향_참조필드1,','정방향_참조필드2',....) # n개 만큼 JOIN 한다. 
            .annotate(커스텀프로퍼티_블라블라=F('모델필드아무거나'),  
                      커스텀프로퍼티2_블라블라=Case(
                          When(Case조건절_모델필드아무거나__isnull=False,  # filter질의는 아무거나 다 가능 __gte, __in 등등...
                               then=Count('특정모델필드')), # 해당 값 기준으로 Count() 함수를 질의함
                          default=Value(0, output_field=IntegerField(
                                      help_text='해당 애트리뷰트 결과값을 django에서 무슨타입으로 받을건지 선언하는 param입니다.'),
                          ),
                      ))
            .filter(각종_질의~~~~)
            .prefetch_related(
                        Prefetch('역방향_참조필드', # 추가 쿼리는 새로운 쿼리셋이다 여기서 쿼리셋에 원하는 튜닝이 가능 
                                       queryset=(역방향_참조모델.objects
                                                 .select_related('역방향_참조모델의_정방향참조모델').filter(역방향_각종_질의문))
                                                # .prefetch_related('역방향_참조모델의_역(정)방향참조모델') 이런식으로도 가능
                                )
            )
            )
```

<br><hr><br>

>SQL
```sql
SELECT *
       모델필드아무거나 AS 커스텀프로퍼티_블라블라,
       CASE
           WHEN Case조건절_모델필드아무거나 IS NOT NULL
               THEN COUNT('특정모델필드')
        ELSE 0 END AS 커스텀프로퍼티_블라블라2,  # IntegerField()는 쿼리에서는 영향없음
      
FROM `orm_practice_app_order`
         LEFT INNER JOIN '정방향 참조필드1'  # INNER OUTER 는 ForignKey(null= True or False 값에 의해 결정
                         ON (~~~~)
         LEFT OUTER JOIN '정방향 참조필드2'  # INNER OUTER 는 ForignKey(null= True or False 값에 의해 결정
                         ON (~~~~)
WHERE (각종_질의~~~~)


SELECT *
FROM 역방향_참조모델
         INNER JOIN '역방향_참조모델의_정방향참조모델'
                    ON (~~~~~)
WHERE (역방향_각종_질의문 AND 메인쿼리의_Model.`related_id` IN (1,2,3,4,....));
```