import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;


public class ImenikDemo {
	
	private static final Imenik imn = new Imenik();
	// TODO - putanja do xml i json fajla
	private static final String xmlPath = "/home/matija/Desktop/writtenXml.xml";
	private static final String jsonPath = "/home/matija/Desktop/writtenJson.json";

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		System.out.println("Imenik - implementacija pomocu xml-a");
		System.out.println("Ucitavanje imenika iz fajla");
		
		
		ucitajImenik();
		menu();
	}

	private static void ucitajImenik() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document imenikIn = docBuilder.parse(ImenikDemo.class.getResourceAsStream("/imenik.xml"));
	
		
		Element root = imenikIn.getDocumentElement();
		parse(root);
		
	}
	
	private static void parse(Element targetNode) {
		Queue<Node> visited = new LinkedList<>();
		visited.add(targetNode);
		
		while(!visited.isEmpty()) {
			Node poll = visited.poll();
			
			if (poll.getNodeName().toLowerCase().equals("kontakt")) {
				Kontakt contact = new Kontakt();
				// parse contact attrs
				parseContact(contact, poll);
				imn.addKontakt(contact);
				
				
				// get next contact
				if (poll.getNextSibling() != null) {
					visited.add(poll.getNextSibling());
				}
			}
			
			else if (poll.hasChildNodes()) {
				for(int i=0; i<poll.getChildNodes().getLength(); i++) {
					Node item = poll.getChildNodes().item(i);
					if (item.getNodeType() == Node.ELEMENT_NODE) {
						visited.add(item);
					}
				}
			}
		}
		
	}
	

	private static void parseContact(Kontakt contact, Node node) {
		int n = node.getChildNodes().getLength();
		for (int i=0; i<n; i++) {
			Node item = node.getChildNodes().item(i);
			if (item.getNodeType() == Node.ELEMENT_NODE) { // && item.getNodeName().matches("ime|prezime|adresa|tel|email")
				try {
					Field field = contact.getClass().getDeclaredField(item.getNodeName());
					field.setAccessible(true);
					Class<?> clazz = field.getType();

					if (clazz.equals(Set.class)) {
						@SuppressWarnings("unchecked")
						Set<String> s = (Set<String>) field.get(contact);
						if (s == null) {
							s = new HashSet<>();
						}
						s.add(item.getFirstChild().getTextContent().trim());
						field.set(contact, s);
					}
					
					else {
						field.set(contact, item.getFirstChild().getTextContent().trim());
					}
					
				} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
					e.printStackTrace();
				}
				
			}
		}
	}
	
	private static void menu() {
		try {
			System.out.println("Imenik - opcije");
			System.out.println("1 - Prikaz imenika");
			System.out.println("2 - Dodavanje kontakta");
			System.out.println("3 - Pretraga");
			System.out.println("4 - Brisanje kontakta");
			System.out.println("0 - Izlazak");
			BufferedReader ulaz = new BufferedReader(new InputStreamReader(System.in));
			String linija;
			while ((linija = ulaz.readLine()) != null) {
				if ("0".equals(linija)) {
					writeXML();
					writeJSON();
					return;
				}
				switch (linija) {
				case "1":
					prikazImenika();
					break;
				case "2":
					dodavanjeKontakta(ulaz);
					break;
				case "3":
					pretragaKontakta(ulaz);
					break;
				case "4":
					brisanjeKontakta(ulaz);
					break;
				default:
					System.out.println("Nepoznata opcija");
					break;
				}
				System.out.println("");
				System.out.println("");
				System.out.println("Imenik - opcije");
				System.out.println("1 - Prikaz imenika");
				System.out.println("2 - Dodavanje kontakta");
				System.out.println("3 - Pretraga");
				System.out.println("4 - Brisanje kontakta");
				System.out.println("0 - Izlazak");
			}
		} catch (IOException ex) {
			ex.printStackTrace(System.err);
		}
	}

	private static void prikazImenika() {
		System.out.println("Sadrzaj imenika");
		System.out.println(imn);
	}

	private static void dodavanjeKontakta(BufferedReader ulaz) throws IOException {
		System.out.println("Unesite ime: ");
		String ime = ulaz.readLine();
		System.out.println("Unesite prezime: ");
		String prezime = ulaz.readLine();
		System.out.println("Unesite adresu: ");
		String adresa = ulaz.readLine();
		System.out.println("Unesite tel: ");
		Set<String> phones = parseCollection(ulaz.readLine());
		System.out.println("unesite mail: ");
		Set<String> mails = parseCollection(ulaz.readLine());
		Kontakt k = new Kontakt();
		k.setIme(ime);k.setPrezime(prezime);k.setAdresa(adresa);k.setEmail(mails);k.setTel(phones);
		imn.addKontakt(k);
	}

	private static Set<String> parseCollection(String readLine) {
		String[] split = readLine.split(",");
		Set<String> ret = new HashSet<>();
		for (String token : split) {
			token = token.trim();
			ret.add(token);
		}
		return ret;
	}

	private static void pretragaKontakta(BufferedReader ulaz) throws IOException {
		System.out.println("Zelite li da pretrazite kontakt po imenu(1)/prezimenu(2)/br telefona(3)");
		int ans = Integer.parseInt(ulaz.readLine());
		switch (ans) {
		case 1:
			System.out.println("Unesite ime");
			String ime = ulaz.readLine();
			System.out.println(imn.findByPredicate(k -> k.getIme().equals(ime)));
			break;
		case 2:
			System.out.println("Unesite prezime");
			String prezime = ulaz.readLine();
			System.out.println(imn.findByPredicate(k->k.getPrezime().equals(prezime)));
			break;
		case 3:
			System.out.println("Unesite tel");
			String tel = ulaz.readLine();
			System.out.println(imn.findByPredicate(k -> {
				return k.getTel().contains(tel);
			}));
			break;
		default:
			break;
		}
	}
	
	private static void brisanjeKontakta(BufferedReader ulaz) throws IOException {
		System.out.println("Unesite ime za zeljeni kontakt");
		String ime = ulaz.readLine();
		System.out.println("unesite prezime za zeljeni kontakt");
		String prezime = ulaz.readLine();
	
		List<Kontakt> forRemoval = imn.findByPredicate(k-> k.getIme().equals(ime) && k.getPrezime().equals(prezime));
		imn.deleteAllContacts(forRemoval);
	}
	
	 public static void writeXML() {

	        try {
	            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	            DocumentBuilder db = dbf.newDocumentBuilder();
	            Document doc = db.newDocument();
	            Element root = doc.createElement("imenik");

	            doc.appendChild(root);
	            
	            
	            for (Kontakt k : imn.getContacts()) {

	                Element kontakt = doc.createElement("kontakt");
	                root.appendChild(kontakt);

	                String contactName = k.getIme();
	                if (contactName != null) {
	                	Element ime = doc.createElement("ime");
	                	ime.appendChild(doc.createTextNode(contactName));
	                	kontakt.appendChild(ime);
	                	
	                }

	                String surname = k.getPrezime();
	                if (surname != null) {
	                	Element prezime = doc.createElement("prezime");
	                	prezime.appendChild(doc.createTextNode(surname));
	                	kontakt.appendChild(prezime);
	                }

	                String address = k.getAdresa();
	                if (address != null) {
	                	Element adresa = doc.createElement("adresa");
	                	adresa.appendChild(doc.createTextNode(address));
	                	kontakt.appendChild(adresa);
	                }

	                
	                if (k.getTel() != null) {
	                	
	                	for (String telefon : k.getTel()) {
	                		Element tel = doc.createElement("tel");
	                		tel.appendChild(doc.createTextNode(telefon));
	                		kontakt.appendChild(tel);
	                	}
	                }


	                if (k.getEmail() != null) {
	                	for (String mail : k.getEmail()) {
	                		Element email = doc.createElement("email");
	                		email.appendChild(doc.createTextNode(mail));
	                		kontakt.appendChild(email);
	                	}
	                }
	            }

	    		TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    		Transformer transformer = transformerFactory.newTransformer();
	    		DOMSource source = new DOMSource(doc);
	    		StreamResult result = new StreamResult(new File(xmlPath));
	    		
	    		transformer.transform(source, result);

	        } catch (ParserConfigurationException | TransformerException ex) {
	        	ex.printStackTrace();
	        } 
	    }
	 
	 public static void writeJSON() {

	        try {

	            JSONArray jsonNiz = new JSONArray();
	            for (Kontakt k : imn.getContacts()) {
	                JSONObject jsonObject = new JSONObject();
	                jsonObject.put("ime", k.getIme());
	                jsonObject.put("prezime", k.getPrezime());
	                jsonObject.put("adresa", k.getAdresa());
	                jsonObject.put("tel", k.getTel());
	                jsonObject.put("email", k.getEmail());
	                jsonNiz.put(jsonObject);
	            }

	            FileOutputStream fos = new FileOutputStream(new File(jsonPath));
	            fos.write(jsonNiz.toString().getBytes());
	            fos.close();

	        } catch (IOException ex) {
	        	ex.printStackTrace();
	        } 
	    }

	}

