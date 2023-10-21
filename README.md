# Gestor Academico
Gestor Academico es una aplicación móvil (Android) para el uso académico. Entre sus distintas funcionalidades se puede encontrar una lista de tareas a realizar, calendario con eventos y exportación de plantillas a PDF.
![image](https://github.com/JonathanAriass/Gestor-Academico/assets/94826968/b76c14b2-8559-4bc5-b3c0-262cdaecc89f)

## Gestión de ramas

Para crear una nueva funcionalidad/feature utilizaremos la metodología [GitFlow](https://www.atlassian.com/es/git/tutorials/comparing-workflows/gitflow-workflow).

En resumen de lo que trata esta metodología es la de crear una nueva rama por cada funcionalidad o feature que se desee implementar. De esta forma conseguiremos que el flujo de trabajo sea más cómodo si se quiere eliminar algo de la aplicación.

Para ello podemos crear una rama de varías formas. La primera de ellas es crear una rama desde la página de GitHub en la interfaz de branch. Normalmente aparecerá como marcada master, que es la rama principal. Si queremos crear una rama de master podemos ir al menú de [branches](https://github.com/JonathanAriass/Gestor-Academico/branches).

![image](https://github.com/JonathanAriass/Gestor-Academico/assets/94826968/df712931-b4d1-4cd7-ae4a-4bdcf62de7d8)


Haremos clic en el botón `New branch` y nos aparecerá este modal:

![image](https://github.com/JonathanAriass/Gestor-Academico/assets/94826968/cabbb64d-c1d0-41e4-8578-37350090214c)


Ahi podemos seleccionar desde donde queremos crear la rama en el seleccionable de `Source`. Si elegimos la rama `develop` se creará una nueva rama con todo el contenido de la rama `develop`. Esto nos facilitará mucho el merge de las ramas para la subida de las nuevas funcionalidades. Siempre que se quiera subir una nueva funcionalidad habrá que mergear la rama `develop` en la rama que se está trabajando para asi comprobar que todo lo anterior sigue funcionando correctamente.

## Autores
*Daniel Uría Edroso - UO282813*

*Aarón Orozco Fernández - UO281997*

*Jonathan Arias Busto - UO283586*
