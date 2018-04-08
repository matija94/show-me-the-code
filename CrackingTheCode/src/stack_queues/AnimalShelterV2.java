package stack_queues;

import java.util.Date;
import java.util.LinkedList;

public class AnimalShelterV2 {

	public final static int DOG = 0;
	public final static int CAT = 1;
	
	public class Animal {
		
		public final int type;
		public final String name;
		private long timestamp;
		
		public Animal(int type, String name) {
			this.type = type;
			this.name = name;
			timestamp = new Date().getTime();
		}
		
	}
	
	private LinkedList<Animal> dogs;
	private LinkedList<Animal> cats;
	
	public AnimalShelterV2() {
		dogs = new LinkedList<>();
		cats = new LinkedList<>();
	}
	
	public void enqueue(Animal animal) {
		if (animal.type == DOG) {
			dogs.add(animal);
		}
		else if (animal.type == CAT) {
			cats.add(animal);
		}
	}
	
	public Animal dequeueAny() {
		Animal dog = dogs.peek();
		Animal cat = cats.peek();
		if (dog != null && cat != null) {
			return dog.timestamp > cat.timestamp ? dogs.poll() : cats.poll(); 
		}
		else if (dog == null) {
			return cats.poll();
		}
		else {
			return dogs.poll();
		}
	}
	
	public Animal dequeueDog() {
		return dogs.poll();
	}
	
	public Animal dequeueCat() {
		return cats.poll();
	}
	
	
	public static void main(String[] args) {
		AnimalShelterV2 shelter = new AnimalShelterV2();
		shelter.enqueue(shelter.new Animal(CAT, "macketina"));
		shelter.enqueue(shelter.new Animal(DOG, "keric"));
		shelter.enqueue(shelter.new Animal(CAT, "macak2"));
		shelter.enqueue(shelter.new Animal(CAT, "macka3"));
		shelter.enqueue(shelter.new Animal(DOG, "ker2"));
		
		
		System.out.println(shelter.dequeueDog().name);
		System.out.println(shelter.dequeueAny().name);
		System.out.println(shelter.dequeueDog().name);
		System.out.println(shelter.dequeueAny().name);
		System.out.println(shelter.dequeueCat().name);
	}
}
