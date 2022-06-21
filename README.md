Original App Design Project - README Template
===

# PocketCloset

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
This app is a fashion app with categories for different articles of clothing such as shirts, pants, shoes, and accessories. Users can upload pictures to these different categories and recieve a different randomized outfit every day. In the randomized outfit, users can swipe each article of clothing to get a new article of clothing until the outfit is to their liking. They can save these outfits and recieve statistics on what are their least worn and most worn clothes.


### App Evaluation
[Evaluation of your app across the following attributes]
- **Category:** Lifestyle, Social, Productivity
- **Mobile:** Camera, Push
- **Story:** After watching the movie Clueless and seeing how Cher had a closet where she could randomize clothing and swipe through her outfits on a screen, I knew I wanted an app that organized my large amount of clothes and gave me a random outfit every day as I had no idea which clothing I barely wore. 
- **Market:** The app is targetted for people who struggle choosing outfits in the morning or maybe have a large amount of clothes and don't know how to pick an outfit from them, or maybe have a small amount of clothing and want a change up in the outfits they wear everyday. 
- **Habit:** Push notifications to remind people of clothes they haven't worn
- **Scope:** This app has difficult components to implement such as the multiple tabs to organize the photographs and being able to give push notifications for when someone should wear a piece of clothing more. However, the app can still be stripped down and still be functional and useful.  

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* Can sign up or log in to an account with a username and password
* Can upload photos of their clothing into categories of articles of clothing or take photos to upload
* Can get a random outfit idea for the day 
* Can swipe each article of clothing in the random outfit if they don't like it
* Can save outfits to a "closet" when selecting okay after randomization 
    * Can view these outfits in the "Outfits" tab 
* Can recieve notifications when an article of clothing hasn't been used 
* Can see their most worn and least worn clothing in the profile section and the number of outfits saved
* Can see profile picture and username in profile view 

**Optional Nice-to-have Stories**

* Each clothing has a details view where user can write where it's from 
    * Can use tags for each clothing and write custom tags in this details view
    * Can organize clothing in each category based on tags
* Can post outfits to app with a profile for each clothing showing where it's from 
    * Can see feed with all real-time posts and can like and save posts
    * saved posts can be seen in profile view
    * User's posts can be seen in profile view


### Difficult/ Ambiguous Technical Problems: 
1) Push notifications to alert when a clothing hasn't been worn
    * notification takes user back to the app
    * have not worked with notifications and push, so need to understand how to get back to app from both banner and screen notifications 

2) Organizing clothing based on tags
    * how to allow users to create a custom tag and organize their closet based on these tags
    * even further, how to randomize outfits based on tags, like making an all formal outfit


3) Showing least worn and most worn clothes in profile 
    * How to filter and decide which clothes are most worn or least worn
    * I need to filter an array of pointers


### Database:
Parse database to organize my data models

### Priority Order: 
1) Users should upload clothes or take pictures and upload to the different categories of clothing
2) Users should be able to randomize outfits with the click of a button
3) Users have a wardrobe where they can save their randomized outfits
4) Users should see a profile with their username and can see how many clothes they have
5) Users should be able to add a profile pic
6) Users should be able to see least worn and most worn clothes on profile
7) Users should be able to see a profile view of clothes when they upload photos which includes where the clothing 
8) Users should be able to add tags to clothing when uploading them and be able to see these tags in profile view
9) Users can organize their "wardrobe" based on tags
10) Users can randomize outfits based on tags
11) Users should be able to post pictures of outfits in a feed
12) Users should be able to like others pictures 
13) Users should be able to save pictures and see saved pictures on their profile
14) When users upload pictures they should be able to tag which clothes from their closet they are wearing so other users can view where their clothes are from





### 2. Screen Archetypes

* [Wardrobe]
   * [Switch to camera view and take pictures to add to each category: hats, acessories, shirts, pants, dresses, shoes]
   * [Be able to tab through the categories]
   * [Be able to see all clothing that user has added in each category]
   * [Be able to click a button that randomizes an outfit]
* [Randomize outfit view]
   * [Be able to randomize an outfit with a random hat, accessory, shirt, pant, and shoe or a hat, accessory, dress, and shoe]
   * [Be able to swipe each article of clothing after the outfit has been randomized until it's an outfit the user likes]
   * [Be able to click okay when user likes outfit, which saves outfit to the "outfits" tab and and brings user back to "Wardrobe" view]
* [Outfits view]
   * [Be able to see all saved outfits]
* [Profile View]
   * [Be able to see username and have a profile picture]
   * [Be able to see how many outfits saved]
   * [Be able to see least worn and most worn clothing]


### 3. Navigation

**Tab Navigation** (Tab to Screen)
 
* [Camera]
* [Outfits]
* [Wardrobe]
* [Profile]
* **optional** [home/feed]


**Flow Navigation** (Screen to Screen)

* [Sign up or log in screen]
    * [Wardrobe tab]
* [Wardrobe tab]
    * [Camera tab]
    * [Outfits tab]
    * [Profile tab]
    * **optional** [home/feed tab]
    * [Hats]
    * [Accessories]
    * [Shirts]
    * [Bottoms]
    * [Shoes]
    * [Randomize outfits]
* [Randomize outfits screen] 
    * [Wardrobe tab]
* [Camera tab]
    * [Wardrobe tab]
* [Outfits tab]
    * [Camera tab]
    * [Wardrobe tab]
    * [Profile tab]
    * **optional** [home/feed tab]
* [Profile tab]
    * [Camera tab]
    * [Outfits tab]
    * [Wardrobe tab]
    * **optional**[home/feed tab]
    * **optional**[Saved posts]
    * **optional**[User's posts]
    * [Most worn clothes]
    * [Least worn clothes]
* **optional** [home/feed tab]
    * [Camera tab]
    * [Outfits tab]
    * [Wardrobe tab]
    * [Profile tab]
    * [Post a picture]
        * [Camera screen]
        * [Write Description/post to feed]



## Wireframes
[Add picture of your hand sketched wireframes in this section]
<img src="https://i.imgur.com/464fQeX.jpg" width=600>


## Schema 

[This section will be completed in Unit 9]
### Models

User Model: 

Columns  | Type | 
| ------------- | ------------- 
| username  | String  |
| password | String  |
| mostWorn | Array of pointers |
| leastWorn | Array of pointers |

Clothing Model: 

Columns  | Type | 
| ------------- | ------------- 
| clothingType  | String  |
| Tags | Array |
| photo | File |

Outfit Model: 
Columns  | Type | 
| ------------- | ------------- 
| Hats  | Pointer  |
| Shirts | Pointer |
| Pants | Pointer |
| Outerwear | Pointer |
| Shoes | Pointer |
| Accessories | Array |

**optional models** : 

Columns  | Type | 
| ------------- | ------------- 
| clothingType  | String  |
| Tags | Array |
| clothingSource | String |
| photo | File |

User Model: 

Columns  | Type | 
| ------------- | ------------- 
| username  | String  |
| password | String  |
| mostWorn | Array of pointers |
| leastWorn | Array of pointers |
| savedPosts | Array of pointers |

Post model: 

Columns  | Type | 
| ------------- | ------------- 
| image  | File  |
| author | Pointer |
| likedBy | Array of pointers |


[Add table of models]
### Networking
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]
