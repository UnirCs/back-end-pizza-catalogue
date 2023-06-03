## Como levantar este microservicio (A partir del tema 9)

- Debes crear un índice en Bonsai.io llamado ``ingredients``. El cuerpo de la petición (mapping) [será este](https://github.com/UnirCs/back-end-pizza-catalogue/blob/master/ingredients_mapping.json).
- Debes ingestar [estos datos](https://github.com/UnirCs/back-end-pizza-catalogue/blob/master/ingredients_raw.json) en el índice de la misma forma que lo hicimos en clase del tema 9 y en los ejercicios.
- Debes desplegar Eureka, Cloud GW (ojo al valor que pones en la variable de entorno ``ALLOWED ORIGINS``) y después este microservicio, incluyendo en el archivo properties o YML los datos de tu clúster.
- Asegúrante de que realizando una petición a ``http://localhost:8762/ms-pizza-catalogue/ingredients`` recibes los datos de los ingredientes.
- Si quieres desplegar el [Front-End](https://github.com/UnirCs/front-end-pizza) debes hacerlo con este comando: ``REACT_APP_GW_URL=http://localhost:8762/ms-pizza-catalogue/ingredients npm start``.
