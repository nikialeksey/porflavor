# PorFlavor

![Elegant Objects Respected Here](http://www.elegantobjects.org/badge.svg)
[![Build Status][build-status-badge]][build-status-link]
[![codecov][codecov-badge]][codecov-link]

[![Gradle plugin version][gradle-plugin-badge]][gradle-plugin-link]
[![License: MIT][license-badge]][license-link]

![nullfree status](https://youshallnotpass.dev/nullfree/nikialeksey/porflavor)
![staticfree status](https://youshallnotpass.dev/staticfree/nikialeksey/porflavor)
![allfinal status](https://youshallnotpass.dev/allfinal/nikialeksey/porflavor)
![allpublic status](https://youshallnotpass.dev/allpublic/nikialeksey/porflavor)
![setterfree status](https://youshallnotpass.dev/setterfree/nikialeksey/porflavor)

# What it is?

PorFlavor is the plugin for extending the android product flavors. When you use multiple dimensions you can not
define unique build config fields or res values per flavor:
```groovy
android {
    flavorDimensions 'brand', 'version'
    
    productFlavors {
        Brand1 {
            dimension 'brand'
        }
        Brand2 {
            dimension 'brand'
        }
        Brand3 {
            dimension 'brand'
        }
        Store {
            dimension 'version'
        }
        Staging {
            dimension 'version'
        }
    }
}
```
and you can not define unique field for `Brand2Staging` flavors. But with **porflavor** plugin you can!

## Getting started

Add **porflavor** plugin:
```groovy
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.com.nikialeksey:porflavor-gradle-plugin:<latest>"
  }
}
```

And then apply it:
```groovy
apply plugin 'com.android.application'
apply plugin: 'com.nikialeksey.porflavor'

android {
    flavorDimensions 'brand', 'version'
        
    productFlavors {
        Brand1 {
            dimension 'brand'
        }
        Brand2 {
            dimension 'brand'
        }
        Brand3 {
            dimension 'brand'
        }
        Store {
            dimension 'version'
        }
        Staging {
            dimension 'version'
        }
    }
    
    porflavor {
        Brand1Store {
            buildConfigField "boolean", "fooFeatureEnabled", "false"
            resValue "string", "appName", "App"
        }
        Brand1Staging {
            buildConfigField "boolean", "fooFeatureEnabled", "false"
            resValue "string", "appName", "App Staging"
        }
    }
}
```

[build-status-badge]: https://github.com/nikialeksey/porflavor/actions/workflows/ci.yml/badge.svg
[build-status-link]: https://github.com/nikialeksey/porflavor/actions/
[codecov-badge]: https://codecov.io/gh/nikialeksey/porflavor/branch/master/graph/badge.svg
[codecov-link]: https://codecov.io/gh/nikialeksey/porflavor
[gradle-plugin-badge]: https://img.shields.io/maven-metadata/v/https/plugins.gradle.org/m2/gradle/plugin/com/nikialeksey/porflavor-gradle-plugin/maven-metadata.xml.svg?label=plugin
[gradle-plugin-link]: https://plugins.gradle.org/plugin/com.nikialeksey.porflavor
[license-badge]: https://img.shields.io/badge/License-MIT-yellow.svg
[license-link]: https://github.com/nikialeksey/porflavor/blob/master/LICENSE
