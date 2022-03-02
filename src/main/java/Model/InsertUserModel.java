package Model;

public class InsertUserModel {
	public String FirstName,
	MiddleName,
	LastName, 
	Level, 
	Email, 
	EmailConfirmed ,
	PasswordHash ,
	SecurityStamp, 
	PhoneNumber,
	PhoneNumberConfirmed ,
	TwoFactorEnabled ,
	LockoutEndDateUtc ,
	LockoutEnabled ,
	AccessFailedCount ,
	UserName,
	VerificationCode, 
	VerificationCodeConfirmed, RoleID;

	
	public InsertUserModel() {
		
	}

	public InsertUserModel(String firstName, String middleName, String lastName, String level, String email,
			String emailConfirmed, String passwordHash, String securityStamp, String phoneNumber,
			String phoneNumberConfirmed, String twoFactorEnabled, String lockoutEndDateUtc, String lockoutEnabled,
			String accessFailedCount, String userName, String verificationCode, String verificationCodeConfirmed) {
		super();
		FirstName = firstName;
		MiddleName = middleName;
		LastName = lastName;
		Level = level;
		Email = email;
		EmailConfirmed = emailConfirmed;
		PasswordHash = passwordHash;
		SecurityStamp = securityStamp;
		PhoneNumber = phoneNumber;
		PhoneNumberConfirmed = phoneNumberConfirmed;
		TwoFactorEnabled = twoFactorEnabled;
		LockoutEndDateUtc = lockoutEndDateUtc;
		LockoutEnabled = lockoutEnabled;
		AccessFailedCount = accessFailedCount;
		UserName = userName;
		VerificationCode = verificationCode;
		VerificationCodeConfirmed = verificationCodeConfirmed;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getMiddleName() {
		return MiddleName;
	}

	public void setMiddleName(String middleName) {
		MiddleName = middleName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getLevel() {
		return Level;
	}

	public void setLevel(String level) {
		Level = level;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getEmailConfirmed() {
		return EmailConfirmed;
	}

	public void setEmailConfirmed(String emailConfirmed) {
		EmailConfirmed = emailConfirmed;
	}

	public String getPasswordHash() {
		return PasswordHash;
	}

	public void setPasswordHash(String passwordHash) {
		PasswordHash = passwordHash;
	}

	public String getSecurityStamp() {
		return SecurityStamp;
	}

	public void setSecurityStamp(String securityStamp) {
		SecurityStamp = securityStamp;
	}

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}

	public String getPhoneNumberConfirmed() {
		return PhoneNumberConfirmed;
	}

	public void setPhoneNumberConfirmed(String phoneNumberConfirmed) {
		PhoneNumberConfirmed = phoneNumberConfirmed;
	}

	public String getTwoFactorEnabled() {
		return TwoFactorEnabled;
	}

	public void setTwoFactorEnabled(String twoFactorEnabled) {
		TwoFactorEnabled = twoFactorEnabled;
	}

	public String getLockoutEndDateUtc() {
		return LockoutEndDateUtc;
	}

	public void setLockoutEndDateUtc(String lockoutEndDateUtc) {
		LockoutEndDateUtc = lockoutEndDateUtc;
	}

	public String getLockoutEnabled() {
		return LockoutEnabled;
	}

	public void setLockoutEnabled(String lockoutEnabled) {
		LockoutEnabled = lockoutEnabled;
	}

	public String getAccessFailedCount() {
		return AccessFailedCount;
	}

	public void setAccessFailedCount(String accessFailedCount) {
		AccessFailedCount = accessFailedCount;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getVerificationCode() {
		return VerificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		VerificationCode = verificationCode;
	}

	public String getVerificationCodeConfirmed() {
		return VerificationCodeConfirmed;
	}

	public void setVerificationCodeConfirmed(String verificationCodeConfirmed) {
		VerificationCodeConfirmed = verificationCodeConfirmed;
	}

	public String getRoleID() {
		return RoleID;
	}

	public void setRoleID(String roleID) {
		RoleID = roleID;
	}
	
	

}
