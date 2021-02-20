<p align="center">
<img src="src/main/resources/icon.png" width="100%" height="128">
<p align="center">
:video_camera: A stupid simple JavaFx app that downgrades Premiere Pro :page_facing_up:s
  <p align="center">
  <img src = "https://i.imgur.com/FnAJSiR.gif">
</p>



## Intro
Premiere Pro has no _official_  option to export save files with **any** backward compatibility. It's either plain XML or nothing at all. If you do choose plain XML you'll find that most of the editing you've done has been lost anyway. This is particularly annoying since you might be opening the file on a version of premiere that is less than a month old, which would probably be able to read the file fine anyway. Premiere Downgrader attempts to solve all of these problems.

## Usage
You can either use the UI by double clicking on the Premiere Downgrader file, duh, or you can use the command line. Just pass the file you want to convert as the first and only arg: `java -jar premiereDowngrader.jar filetodowngrade.prproj` 

## How
Premiere files are just gzipped xml if you unzip one and change to the number on or before your current version, Premiere is more than happy to accept the file. That's all this does. It opens the _.prproj_ file changes the version to `1` and let's your version of premiere upgrade it to whatever it wants later.

## Risks
Technically things could still break, but, by using this method far more of your editing is likely to be preserved than with the offical solution. Furthermore, the only things that might break are features that only exist on the newer version, features which your project is _unlikely_ to be composed of.

## Cool ~~stuff~~ buzzwords used in this project
- RxJava
- RxKotlin
- TornadoFx
- RxKotlinFx
- OkIo
- Dagger
- Bodymovin

## Help
I guess... open a bug request. 

## Building

`gradle jar`

## See also
- More in depth stackexchange discussion on downgrading: https://video.stackexchange.com/a/21365/18870
- How this thing actually does anything: https://stackoverflow.com/a/37283543/1649917

## Images used
- Material icons: Apahce 2
- Feather icons: MIT
- Loading: https://www.lottiefiles.com/528-spinner-loading 

## Contibuting
See CONTIBUTING.md:


tl;dr YES! :raised_hands:

