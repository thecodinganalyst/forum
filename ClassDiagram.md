```mermaid
classDiagram
    Topic "1" *-- "*"Post
    User --> Post : write
    User --> Topic : start
    User: +String userName
    
    Topic: +String title
    Topic: +DateTime created
    Topic: +DateTime updated
    
    Post: +String text
    Post: +DateTime created
    Post: +DateTime updated  