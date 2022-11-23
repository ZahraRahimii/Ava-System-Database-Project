# AVA-database-Project
Here are the step of implementing database of AVA application (perfrom same as Twitter):

* phase 1: Define entities and features
* phase 2: Define procesdures and triggers and implement user interface (UI) and graphical user interface (GUI)

## Define entities and features
First, we must have a general view of the entities and the relationship between them, which can be achieved with ERD model.
<p align="center">
<img src = "https://user-images.githubusercontent.com/93929227/203502176-a9276627-75a7-4156-86c0-28a7f3794052.png" width = "70%" height = "70%">
<p/>

After that we came to these entities:                                              
* user: including name, famliy name, user name, password, birthday, membership date, and bio 
* message: indluding content, ava_id sender_id delivered_id send_date
* hashtag: including content with length 5 character
* ava: including user_name, content, and sendd date

Some of the most important featuers and capabilites are:
* send_message: Every user of the system must have the ability to send messages to other users. This can be done if the other user has not blocked the sender. Each message is either a textual content or an Ava
* get_messages_senders_list: Every user of the system should be able to receive the list of people who have already sent him a valid message. The order of the list is based on the sending time of the last message of each user in descending order.
* get_messages_from: Every user of the system should be able to receive the list of messages sent to him by a particular user. Any message whose content is Ava and the original sender of the Ava has blocked the receiver of the message will not appear in the output list and that message is not valid
* get_comments_of_ava: Each user of the system must be able to receive comments of one voice. If the sender has blocked the receiving user's voice, the received list will be empty
* add_comment: Every user of the system must have the ability to comment on the voices of other users who have not blocked him. Each comment is saved as a vote, so it is possible to comment on another comment

