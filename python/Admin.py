from firebase import firebase

DEBUG = False
MyFirebase = firebase.FirebaseApplication('https://library-caee8.firebaseio.com/', None)


class MyLibrary(object):

    def __init__(self):
        print()

    def create_new_user(self, _username, password):
        my_lib =  MyLibrary()
        isExist = my_lib.is_user_exit(_username)
        if isExist:
            return 'The user is  already exists in the system'
        else:
            MyFirebase.put('/users/' + _username + '/', 'password', password)
            return 'User successfully added'

    def create_root_user(self, username, password):
        MyFirebase.put('/Admin/', username + '/password', password)
        MyFirebase.put('/Admin/', username + '/email', " ")
        MyFirebase.put('/Admin/', username + '/phone', " ")
        MyFirebase.put('/Admin/', username + '/address', " ")

    def update_user_details(self, username, password, mail, cellphone, country, city):
        lib = MyLibrary()
        if lib.is_user_exit(username):
            MyFirebase.put('/users/', username + '/password', password)
            MyFirebase.put('/users/', username + '/email', mail)
            MyFirebase.put('/users/', username + '/phone', cellphone)
            MyFirebase.put('/users/', username + '/Country', country)
            MyFirebase.put('/users/', username + '/City', city)
            return 'Username ' + username + ' updated Successfully'
        else:
            return username + ' is not exist in the database'

    def delete_exist_user(self, username):
        lib = MyLibrary()
        if lib.is_user_exit(username):
            MyFirebase.delete('/users/' + username, None)
            return 'User deleted successfully.'
        else:
            return 'User is not in the database'

    def print_all_usernames(self):
        username_list_employ = MyFirebase.get('/employ/', None)
        username_list = MyFirebase.get('/users/', None)
        print("Users in database are:")
        if username_list is None:
            print('There are no users in the database')
        else:
            print("Users in database: \n")

            for user in username_list:
                print(user)

        if username_list_employ is None:
            print('There are no employes in the database')
        else:
            print("Employes in database: \n")
            for employ in username_list_employ:
                print(employ)

    def is_user_exit(self, username):
        result = MyFirebase.get('/users/', username)
        if result is None:
            print("username dose not exist")
            return False
        print("username  exists")
        return True

    def delete_databaste(self):
        MyFirebase.delete('/', None)
        return 'Delete all usernames from database'


def adminLogin():
    my_lib = MyLibrary()
    isAdminLogin = False
    print("Admin Access")
    print("Enter Admin username:")
    log_user = input()
    while log_user.__eq__(""):
        print("You must enter correct username")
        log_user = input()
    print("Enter Admin Password")
    log_pass = input()
    while log_pass.__eq__(""):
        print("You must enter correct password")
        log_pass = input()
    if log_user.__eq__("matan") and log_pass.__eq__("1234") or log_user.__eq__("yotam") and log_pass.__eq__("2222"):
        isAdminLogin = True
        my_lib.create_root_user(log_user, log_pass)
    while not isAdminLogin:
        print("invalid username or password please try again")
        adminLogin()
    return isAdminLogin


def displayAdminMenu():
    if adminLogin():
        mainMenu()


def mainMenu():
    my_library = MyLibrary()
    print("Welcome To The Library")
    print("Please enter the number of the action you want to execute:")
    print("1.Create new user")
    print("2.Is user exist")
    print("3.Delete username from database")
    print("4.Delete database")
    print("5.Update user information")
    print("6.Print all the users in the database")
    print("7.Quit Menu")
    number = input()
    if number.__eq__("1"):
        print("Create new username")
        print("Enter username:")
        username = input()
        while username.__eq__(""):
            print("Error enter username again:")
            username = input()
        print("Enter Password:")
        password = input()
        while password.__eq__(""):
            print("Error enter password again:")
            password = input()
        print(my_library.create_new_user(username, password))
        mainMenu()
    elif number.__eq__("2"):
        print("Enter username")
        username = input()
        while username.__eq__(""):
            print("Error enter username again:")
            username = input()
        print(my_library.is_user_exit(username))
        mainMenu()
    elif number.__eq__("3"):
        print("Enter username")
        username = input()
        while username.__eq__(""):
            print("Error enter username again:")
            username = input()
        print(my_library.delete_exist_user(username))
        mainMenu()
    elif number.__eq__("4"):
        print("Are you sure you want to delete database? prees Y/N")
        ok = input()
        while ok.__eq__(""):
            print("Error enter Y/N again:")
            ok = input()
        if ok.__eq__("Y"):
            print(my_library.delete_databaste())
            mainMenu()
        else:
            mainMenu()
    elif number.__eq__("5"):
        print("Update section:")
        print("Enter username")
        username = input()
        while username.__eq__(""):
            print("Error enter username again:")
            username = input()
        print("Enter Password")
        password = input()
        while password.__eq__(""):
            print("Error enter password again:")
            password = input()
        print("Enter Email")
        email = input()
        print("Enter CellPhone")
        cellPhone = input()
        print("Enter Country")
        country = input()
        print("Enter City")
        city = input()
        print(my_library.update_user_details(username, password, email, cellPhone, country, city))
        mainMenu()
    elif number.__eq__("6"):
        print(my_library.print_all_usernames())
        mainMenu()
    elif number.__eq__("7"):
        return


def main():
    displayAdminMenu()


if __name__ == '__main__':
    main()
