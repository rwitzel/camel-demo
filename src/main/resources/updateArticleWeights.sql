INSERT INTO ias_article_weight (sku, warehouse, weight) VALUES
{{#body}}
    ( '{{sku}}' , 'EF' , {{efWeight}} ),
    ( '{{sku}}' , 'MG' , {{mgWeight}} ),
    ( '{{sku}}' , 'GB' , {{gbWeight}} ),
{{/body}}
;