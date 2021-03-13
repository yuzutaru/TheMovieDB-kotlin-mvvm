# TheMovieDB-kotlin-mvvm
Application to show movie list from themoviedb.org

1. Main Menu / Movie List menu
  - get the popular themoviedb.org, GET /movie/popular on first load
  - Show bottom sheet on category button click
  - show from themoviedb.org base on category. Except favorite.
  - there are 4 categories : Popular (GET /movie/popular), Top Rated (GET /movie/top_rated), Now Playing (GET /movie/now_playing), Favorite (local db)
  - favourite category is base on local db, add by clicking love icon on detail movie menu
  - favourite list can also be shown by clicking heart icon on the top right of the screen
  - Pagination support using androidx paging2
  - load image using glide
  
2. Movie Detail Menu
  - When item list on movie menu clicked, go to this menu
  - show the detail data from GET /movie/{movie_id} api
  - show the review of the movie from GET /movie/{movie_id}/reviews
  - support pagination using androidx paging2 on review list
  - click love icon to add to favourite
  - load image using glide
