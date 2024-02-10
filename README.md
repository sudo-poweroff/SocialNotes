# 📖 SocialNotes
![SocialNotes_homepage](https://github.com/sudo-poweroff/SocialNotes/blob/master/WebContent/img/homePageSocialNotes.png)
University study is challenging and students often face several difficulties in approaching their exams. The breadth of content, complexity of concepts, and tight preparation time can create a stressful and demanding environment.
In this context, students turn to various study resources, including lecture notes, recommended textbooks, and online resources. However, finding the right resources can also be a complex and time-consuming task.
For these reasons, SocialNotes was created to provide university students with a platform on which to find or upload study material to make it available to other students. The site was created not only to purchase notes but also to create a platform that guarantees interaction between registered users. This interaction takes place through the use of individual or group chats that allow users to connect and exchange messages, also allowing the users to socialize.

## ⚙️ Requirements
To install, compile, build, run and test our website you should have these requirements on your machine:
* MySQL vers. = 8.1.0;
* MySQLWorkbench (optional);
* Java 19;
* Tomcat vers. = 9.0.80;

__Requirements for testing__:
* JUnit 4.13.2;
* Selenium IDE vers. = 3.17.2;
* ChromeDriver vers. = 117.0.5938.92

## 🗒 Installation guide
To run our website you should follow these steps:
* Clone GitHub repo: `git clone https://github.com/sudo-poweroff/SocialNotes.git`;
* Create and populate the database through this file: `database/SocialNotes.sql`;
* Edit the `WebContent/META-INF/context.xml` to specify the database username and password. Eventually, you can also edit the MySQL port (default 3306);
* Deploy the website on the Tomcat server;
* Start the server and open your browser on the following URL: `http://localhost:8080/SocialNotes`

## 🗒 Testing guide
You can run tests on your IDE with standard test run configuration.
Alternatively, to run tests you should follow these steps:
* Locate `test/run_test.sh`;
* Edit Java home, project directory and test class according to your configuration;
* Execute the `test/run_test.sh` file.

## 👨🏻‍💻 Contributors
<a href="https://github.com/sudo-poweroff/SocialNotes/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=sudo-poweroff/SocialNotes" />
</a>
