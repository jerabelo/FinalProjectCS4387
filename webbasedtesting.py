from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
import time, string, random, secrets

dictionary = {}

driver = webdriver.Chrome("C:\Program Files (x86)\chromedriver.exe")

#getting email
driver.get("https://www.minuteinbox.com/")
email = driver.find_element_by_id("email").text
dictionary["email"] = email
time.sleep(0.5)
#getting full name
driver.get("https://www.name-generator.org.uk/")
names = driver.find_element_by_partial_link_text('Quick Name')
names.click()
fullname=driver.find_element(By.CSS_SELECTOR, "div[class='name_heading']").text
firstname = fullname.split()[0]
lastname = fullname.split()[1]
dictionary["firstname"] = fullname
dictionary["lastname"] = lastname
time.sleep(0.5)
#Adding password to the dictionary
password_length = 13
dictionary["password"] = secrets.token_urlsafe(password_length)
print(dictionary)
time.sleep(0.5)
#Entering target website & clicking commands
driver.get("https://www.express.com/register")
driver.find_element_by_name("loginName").send_keys(dictionary.get('email'))
time.sleep(0.5)
driver.find_element_by_name("firstname").send_keys(dictionary.get('firstname'))
time.sleep(0.5)
driver.find_element_by_name("lastname").send_keys(dictionary.get('lastname'))
time.sleep(0.5)
driver.find_element_by_name("password").send_keys(dictionary.get('password'))
time.sleep(0.5)
driver.find_element_by_id("rvndd--country--1").click()
time.sleep(0.5)
driver.find_element_by_id("rvndd--country--1").click()
time.sleep(0.5)
driver.find_element_by_css_selector("btn _37UdwV18 XnTsKiDr _20VnLhFF VKXzRdid+Eq7qFjQR52EGQ==").click()
driver.find_element_by_name("Value").send_keys(Keys.ENTER)



