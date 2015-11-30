package com.myproject.model;


import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.text.WordUtils;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import com.myproject.tools.DesEncrypter;
import com.opensymphony.xwork2.ActionSupport;

@Entity
@Table(name="USER")
public class User extends ActionSupport{

	private static final long serialVersionUID = 8526068046627838886L;
	
	public static final int ADMIN = 1;
	public static final int REFEREE = 2;
	public static final int BOTH = 3;

	public static final int USERROLES = 3;
	
	public User(User user){
		super();
		this.idUser = user.getIdUser();
		this.userName = user.getUserName();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.userRole = user.getUserRole();	
		this.userProfile = user.getUserProfile();
		this.privacy = user.isPrivacy();
		this.refereeAvailability = user.getRefereeAvailability();
		this.refereeGames = user.getRefereeGames();
		this.userRefereeTypes = user.getUserRefereeTypes();
	}
	
	public User(String userName, String email, int userRole, UserProfile userProfile, boolean privacy){
		super();
		this.userName = userName;
		this.email = email;
		this.password = "";
		this.userRole = userRole;	
		this.userProfile = userProfile;
		this.privacy = privacy;
		this.refereeAvailability = new LinkedList<RefereeAvailability>();
		this.refereeGames = new LinkedList<RefereeGame>();
		this.userRefereeTypes = new LinkedList<UserRefereeType>();

	}
	
	public User(){
	}
	
	@Id
	@Column(name = "idUSER")
	@GeneratedValue
	private String idUser;
	
	@Column(name="USERNAME", nullable = false, unique = true, length = 45)
	private String userName;
	
	@Column(name="EMAIL", nullable = false, unique = true, length = 45)
	private String email;

	@Column(name="PASSWORD", nullable = false, length = 45)
	private String password;
	
	@Column(name = "USER_ROLE", nullable = false)
	private int userRole;
	
	@OneToOne(cascade = CascadeType.ALL)
	@ForeignKey (name = "FK_USER__USER_PROFILE")
	@JoinColumn(name = "USER_PROFILE", nullable = false, unique = true)
	private UserProfile userProfile;

	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Column(name = "PRIVACY", nullable = false)
	private boolean privacy;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy="user")
    private List<RefereeAvailability> refereeAvailability;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy="user")
    private List<RefereeGame> refereeGames;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy="user")
    private List<UserRefereeType> userRefereeTypes;
	
	public String getIdUser() {
		return idUser;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
        DesEncrypter decrypter = new DesEncrypter(getText("loginPass"));
		return decrypter.decrypt(password);
	}
	
	public int getUserRole() {
		return userRole;
	}
	
	public UserProfile getUserProfile() {
		return userProfile;
	}
	
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setPassword(String password) {

		DesEncrypter encrypter = new DesEncrypter(getText("loginPass"));
		this.password =	encrypter.encrypt(password);
	}
	
	public void setUserRole(int userRole) {
		this.userRole = userRole;
	}
	
	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
	
	public boolean isPrivacy() {
		return privacy;
	}

	public void setPrivacy(boolean privacy) {
		this.privacy = privacy;
	}

	public List<RefereeAvailability> getRefereeAvailability() {
		return refereeAvailability;
	}

	public void setRefereeAvailability(List<RefereeAvailability> refereeAvailability) {
		this.refereeAvailability = refereeAvailability;
	}

	public List<RefereeGame> getRefereeGames() {
		return refereeGames;
	}

	public void setRefereeGames(List<RefereeGame> refereeGames) {
		this.refereeGames = refereeGames;
	}
	
	public List<UserRefereeType> getUserRefereeTypes() {
		return userRefereeTypes;
	}

	public void setUserRefereeTypes(List<UserRefereeType> userRefereeTypes) {
		this.userRefereeTypes = userRefereeTypes;
	}

	public String getUserFullName(){
		
		return WordUtils.capitalize(getUserProfile().getFirstName() + " "
				+ getUserProfile().getLastName1() + " " 
				+ ((getUserProfile().getLastName2() == null) ? "" : getUserProfile().getLastName2()));
	}
	
	public boolean isAdmin(){
		
		return userRole == ADMIN || userRole == BOTH;
		
	}
	
	public String getUserRoleName(){
		
		switch (userRole){
		
			case ADMIN: 	return "Designador";
			case REFEREE:	return "Árbitro";
			default:		return "Designador y Árbitro";
				
		}
	}
	
	public boolean getUserRefereeType(int idUserRefereeType){
		
		return this.userRefereeTypes.stream().filter(userRefereeType -> 
				userRefereeType.getRefereeType() == idUserRefereeType).count() == 1 ;
	}
	
	public boolean hasOtherGame(Game game){
		
		return this.refereeGames.stream().filter(refereeGame -> 
			!refereeGame.getGame().getIdGame().equals(game.getIdGame()) 
			&& ((refereeGame.getGame().getGameDate().compareTo(game.getGameDate()) >= 0
				&& refereeGame.getGame().getGameDate().compareTo(game.getGameEndDate()) <= 0)
				||
				(refereeGame.getGame().getGameEndDate().compareTo(game.getGameDate()) >= 0
				&& refereeGame.getGame().getGameEndDate().compareTo(game.getGameEndDate()) <= 0))).count() > 0;
	}
	
	@Override
	public boolean equals(Object o){
		
		boolean result = false;
		if(o instanceof User){
			User usr = (User)o;
			result = usr.getIdUser().equals(this.idUser);
		}

		return result;
	}

}
