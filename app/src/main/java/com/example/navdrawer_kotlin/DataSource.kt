package com.example.navdrawer_kotlin

import com.example.navdrawer_kotlin.model.BlogPost

class DataSource{

    companion object{

        fun createDataSet(): ArrayList<BlogPost>{
            val list = ArrayList<BlogPost>()
            list.add(
                BlogPost(
                    "21 Potentially Life-Saving Safety Tips That Every Woman Should Know",
                     "https://hips.hearstapps.com/cos.h-cdn.co/assets/cm/14/28/480x320/53a047fd8a152_-_cos-01-taken-grace-de.jpg?resize=980:*",
                    "COSMOPOLITIAN"
                )
            )
            list.add(
                BlogPost(
                    "7 things women do to stay safe, that men don't have to",
                    "https://akm-img-a-in.tosshub.com/indiatoday/images/story/201704/girlsstory_647_042517033434.jpg?size=770:433",
                    "INDIA TODAY"
                )
            )

            list.add(
                BlogPost(
                    "International Women’s Day 2018: 5 safety tips for women",
                    "https://assets.publishing.service.gov.uk/government/uploads/system/uploads/image_data/file/116152/s300_Int-Womens-Day-2021-image.jpg",
                    "The Free Press Juornal"
                )
            )
            list.add(
                BlogPost(
                    "Women's safety: Smartphone tips shared online",
                    "https://ichef.bbci.co.uk/news/976/cpsprodpb/0663/production/_117553610_woman-phone.jpg",
                    "BBC News"
                )
            )
            list.add(
                BlogPost(
                    "TOP 10 SAFETY TIPS FOR WOMEN",
                    "https://issuesiface.com/img/containers/mag-headers/top-10-safety-tips-for-women_flickr.jpg/c43e24f74a8c46d0a9b55305221d14e0.jpg",
                    "Richelle"
                )
            )
            list.add(
                BlogPost(
                    "WOMEN HELPLINE NUMBERS",
                    "https://www.helplinecenter.org/wp-content/uploads/helplinelogo2021.jpg",
                    "IndianHelpLine"
                )
            )
            list.add(
                BlogPost(
                    "8 Self-Defense Moves Every Woman Needs to Know",
                    "https://images-prod.healthline.com/hlcmsresource/images/topic_centers/2018-8/8465-woman_self_defense_move-1296x728-header.jpg",
                    "healthline"
                )
            )
            list.add(
                BlogPost(
                    "First Aid Tips",
                    "https://media.istockphoto.com/photos/firstaid-kit-with-all-essential-elements-picture-id1263932003?k=20&m=1263932003&s=612x612&w=0&h=Df0qSoBy6i5iHUeo52oVDYOTwol93NS2xgF0nOMG9N4=",
                    "Vikaspedia"
                )
            )
            list.add(
                BlogPost(
                    "Preventative Measures Against Rape",
                    "data:image/jpeg;base64,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",
                    "Shorewood"
                )
            )
            return list
        }
    }
}