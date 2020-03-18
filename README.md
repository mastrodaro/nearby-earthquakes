# Reason
Locate nearby earthquakes

# Description
This application utilizes [USGS Earthquake Hazards Program](https://earthquake.usgs.gov/aboutus/) to locate recent (10) earthquakes happening nearby given coordinates. The list will be sorted by the distance from the nearest to the farest. Each eathquake will be formatted as `title` field followed by ` || ` and `distance` (rounded to full kilometers).

# Results
For given input:
```
40.730610
-73.935242
```
you will receive:
```
M 1.3 - 2km SSE of Contoocook, New Hampshire || 331
M 1.3 - 2km ENE of Belmont, Virginia || 354
M 2.4 - 83km ESE of Nantucket, Massachusetts || 406
M 1.3 - 13km ENE of Barre, Vermont || 410
M 0.7 - 18km NW of Norfolk, New York || 476
M 2.0 - 17km NW of Norfolk, New York || 476
M 1.7 - 19km NNW of Beaupre, Canada || 758
M 1.9 - 13km SW of La Malbaie, Canada || 814
M 2.4 - 16km N of Lenoir, North Carolina || 840
M 2.4 - 12km ESE of Carlisle, Kentucky || 896
```

# Technologies used
