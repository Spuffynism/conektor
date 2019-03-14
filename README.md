# Conektor

Conektor's a service that makes third party apps available through a Facebook Messenger bot.

## Refactoring

- [In progress] - Split services into services and repositories
- [TODO] - split repositories in repositories & data access layer
- [TODO] - Replace Date usage with LocalDate
- [TODO] - Replace int ids with value objects
- [TODO] - Extract Spring usage to hexagonal model outside layer only
    - Use custom dependency injection container
    - Use custom reflection for calling providers' actions 
        - See http://hannesdorfmann.com/annotation-processing/annotationprocessing101
        - See https://medium.com/@iammert/annotation-processing-dont-repeat-yourself-generate-your
        -code-8425e60c6657