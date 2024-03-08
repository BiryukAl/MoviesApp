# Movies App

Do: Бирюков Aлександр

Contact: [Telegram](https://t.me/SanyaLn), [HH.ru](https://kazan.hh.ru/resume/c458e78eff0c0dd3760039ed1f7047504d6247)

## Test task:
Main:

[+] 1. На главном экране необходимо отображать список популярных фильмов.

[+] 2. В каждой карточке фильма на главной странице должны содержаться следующие элементы:
- 2.1 Наименование фильма.  
- 2.2 Изображение-постер фильма.  
- 2.3 Год выпуска.
 
[+] 3. При клике на карточку открывается экран с постером фильма, описанием, жанром, страной
производства.

[+] 4. Если сеть недоступна или в процессе загрузки произошла ошибка, необходимо предусмотреть
уведомление пользователя об этом.

Option:

[-] 1. При смене ориентации устройства, список фильмов занимает только 50% экрана, во второй
половине будет отображаться описание фильма.

[+] 2. На главном экране присутствуют разделы «Популярное» и «Избранное». При длительном клике на
карточку, фильм помещается в избранное и хранится в базе данных. Карточки фильмов из
избранного доступны в оффлайн-режиме.

[+] 3. При просмотре популярных, выделяются фильмы, находящиеся в избранном.

[+] 4. В разделах доступен поиск фильмов по наименованию (в соответствии с выбранным разделом).

[+] 5. Обеспечена общая плавность и стабильность приложения.

[+] 6. Во время длительных загрузок, отображаются шиммеры/прогресс бары.

[-] 7. Ответы от API должны быть закешированы хотя бы на время сессии.

[-] 8. Приложение покрыто UNIT тестами.

## Result:

| Screeen Popolar                      | Screeen Popolar Dark                    | Screeen Favorite                      | Screeen Details                      |
|--------------------------------------|-----------------------------------------|---------------------------------------|--------------------------------------|
| ![Photo](/README/screen_popular.png) | ![Photo](/README/screen_dark_theme.png) | ![Photo](/README/screen_favorite.png) | ![Photo](/README/screen_details.png) |

| Screen Search                       | Screen Not Found                              | Screen Error Network               | Screen Alert Add Film              |
|-------------------------------------|-----------------------------------------------|------------------------------------|------------------------------------|
| ![Photo](/README/screen_search.png) | ![Photo](/README/screen_search_not_found.png) | ![Photo](/README/screen_error.png) | ![Photo](/README/screen_alert.png) |

## Description:

### 1) Design:

![Figma Design All Version Photo](/README/design.png)
[Figma Link](https://www.figma.com/file/DqAfJbnm1vJNUOtU9iE1Sb/Fintech2023-(Copy)?type=design&node-id=0%3A1&mode=design&t=8DWdMiAxqzZe2RSt-1)

### 2) Modules:

- app
  - core
    - db 
    - designsystem
    - navigation
    - network
    - utils
    - widget
  - feature
    - details
      - api
      - impl  
    - favorites
      - api
      - impl
    - popular
      - api
      - impl
    - search
      - api
      - impl
    
### 2) API: [Неофициальный API кинопоиска](https://kinopoiskapiunofficial.tech/)

### 3) Presentation Layer
  - JetpackCompose
  - MVI
  - Navigation: [Voyager](https://voyager.adriel.cafe/)
  - Coil

### 4) DI: 
  Koin

### 5) Data: 
   Cach: Realm 
   Network: Ktor + Kotlin Serialization 

### 7) Dependency versioning via .toml file
