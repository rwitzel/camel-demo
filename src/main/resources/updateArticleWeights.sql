
  INSERT INTO ias_article_weight (sku, warehouse, weight) VALUES
  {{#body}}
    {{#demos.ArticleWeights}}
      ( '{{sku}}' , 'EF' , {{efWeight}} ),
      ( '{{sku}}' , 'MG' , {{mgWeight}} ),
      ( '{{sku}}' , 'GB' , {{gbWeight}} ),
    {{/demos.ArticleWeights}}
  {{/body}}
  ;