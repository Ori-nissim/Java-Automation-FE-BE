/**
* This class stores all the selectors/ xpath / classes we need in order to access the elements in the program.
*/
public class Selectors {
		// test 2
		public static final String SIGN_IN_BUTTON_SELECTOR = "a.nav__button-secondary.btn-md.btn-secondary-emphasis";
		public static final String USERNAME_FIELD_SELECTOR = "username";
		public static final String PASSWORD_FIELD_SELECTOR = "password";
		public static final String SUBMIT_LOGIN_BUTTON_SELECTOR = "button.btn__primary--large.from__button--floating";
		// test 3
		public static final String PROFILE_PIC_SELECTOR = "//*[@id=\"ember17\"]";
		public static final String VIEW_PROFILE_LINK_SELECTOR = "View Profile";
	    public static final String PROFILE_NAME_SELECTOR = "h1.text-heading-xlarge.inline.t-24.v-align-middle.break-words";
	    public static final String PROFILE_CITY_SELECTOR = "span.text-body-small.inline.t-black--light.break-words";
	    public static final String EXPERIENCE_SECTION_SELECTOR = "section.artdeco-card.pv-profile-card.break-words";
	    public static final String WORKPLACE_ELEMENT_SELECTOR = "//*[@id=\"profile-content\"]/div/div[2]/div/div/main/section[6]/div[3]/ul/li[1]/div/div[2]/div[1]/div/span[1]/span[1]";
	    // test 4
	    public static final String MY_NETWORK_BUTTON_SELECTOR = "/html/body/div[5]/header/div/nav/ul/li[2]/a";
	    public static final String CONNECTIONS_BUTTON_SELECTOR = "div.mn-community-summary__info-container.t-black.t-16.t-normal";
	    public static final String CONNECTION_LIST_SELECTOR = "mn-connection-card__details";
	   
	    // Connection Data
	    public static final String CONNECTION_NAME_SELECTOR = "span.mn-connection-card__name.t-16.t-black.t-bold";
	    public static final String CONNECTION_OCCUPATION_SELECTOR = "span.mn-connection-card__occupation.t-14.t-black--light.t-normal";
	    public static final String CONNECTION_DURATION_SELECTOR = "time.time-badge.t-12.t-black--light.t-normal";

}
