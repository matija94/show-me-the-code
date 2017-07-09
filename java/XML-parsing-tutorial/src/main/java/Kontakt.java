import java.util.Set;

public class Kontakt {
	
	private String ime;
	private String prezime;
	private String adresa;
	private Set<String> tel;
	private Set<String> email;

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public Set<String> getTel() {
		return tel;
	}

	public void setTel(Set<String> tel) {
		this.tel = tel;
	}

	public Set<String> getEmail() {
		return email;
	}

	public void setEmail(Set<String> email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Kontakt [ime=" + ime + ", prezime=" + prezime + ", adresa=" + adresa + ", tel=" + tel + ", email="
				+ email + "]";
	}

	
}
