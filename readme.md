# PorFlavor

![Elegant Objects Respected Here](http://www.elegantobjects.org/badge.svg)

![nullfree status](https://iwillfailyou.com/nullfree/nikialeksey/porflavor)

[![Build Status](https://travis-ci.org/nikialeksey/porflavor.svg?branch=master)](https://travis-ci.org/nikialeksey/porflavor)

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://github.com/nikialeksey/porflavor/blob/master/LICENSE)

# What it is?

ProFlavor is the plugin for extending the android product flavors. When you use multiple dimensions you can not
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
    classpath "gradle.plugin.com.nikialeksey:porflavor-gradle-plugin:0.1.0"
  }
}
```

And then apply it:
```groovy
apply plugin 'com.android.application'

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
    
    apply plugin: 'com.nikialeksey.porflavor'
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
