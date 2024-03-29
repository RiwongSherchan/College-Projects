package application.model;

public class Candidate {

    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String country;
    private String password;
    private String contactNumber;
    private String age;

    // Constructors, getters, and setters

 

    // Getters and setters for each attribute

    public String getFirstName() {
        return firstName;
    }
    
    @Override
    public String toString() {
        // Customize the string representation for display in ComboBox
        return firstName + " (" + gender + ", " + country + ")";
    }

    public Candidate(String firstName, String lastName, String email, String gender, String country, String password,
			String contactNumber) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
		this.country = country;
		this.password = password;
		this.contactNumber = contactNumber;
	}
    
    public Candidate(String firstName, String lastName, String email, String gender, String country, String password,
			String contactNumber,String age) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
		this.country = country;
		this.password = password;
		this.contactNumber = contactNumber;
		this.age = age;
	}

	public Candidate(String firstName, String lastName, String country) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.country = country;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
}
