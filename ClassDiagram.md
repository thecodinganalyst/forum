```mermaid
classDiagram
    Topic "1" *-- "*"Post
    User --> Post : write
    User --> Topic : start
    User: +String userName
    Topic: +String title
    Topic: +DateTime startedOn
    Post: +String text
    Post: +DateTime postedOn  