import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Imenik {
	private List<Kontakt> imenik;

	public Imenik() {
		this.imenik = new ArrayList<>();
	}

	public List<Kontakt> getContacts() {
		return imenik;
	}
	
	public void addKontakt(Kontakt kon) {
		this.imenik.add(kon);
	}
	
	public List<Kontakt> findByPredicate(Predicate<? super Kontakt> predicate) {
		List<Kontakt> collect = imenik
		.stream()
		.filter(predicate)
		.collect(Collectors.toList());
	
		return collect;
	}

	public void deleteContact(Kontakt contact) {
		imenik.remove(contact);
	}
	
	public void deleteAllContacts(List<Kontakt> contacts) {
		imenik.removeAll(contacts);
	}

	@Override
	public String toString() {
		return "Imenik [imenik=" + imenik + "]";
	}
	
	
}