package stack_queues;

import java.util.Iterator;
import java.util.LinkedList;

public class AnimalShelter {

	public final static int DOG = 0;
	public final static int CAT = 1;
	
	public class Animal {
		int animalType;
		String name;
		
		public Animal(int type, String name) {
			animalType = type;
			this.name = name;
		}
	}
	
	private LinkedList<Animal> shelter;
	
	public AnimalShelter() {
		this.shelter = new LinkedList<>();
	}
	
	public void enqueue(Animal animal) {
		shelter.add(animal);
	}
	
	public Animal dequeueAny() {
		return shelter.removeFirst();
	}
	
	public Animal dequeueDog() {
		return dequeueAnimal(DOG);
	}
	
	public Animal dequeueCat() {
		return dequeueAnimal(CAT);
	}
	
	private Animal dequeueAnimal(int type) {
		Iterator<Animal> iterator = shelter.iterator();
		Animal animal = null;
		while(iterator.hasNext()) {
			animal = iterator.next();
			if (animal.animalType == type) {
				break;
			}
		}
		if (animal != null) {
			shelter.removeFirstOccurrence(animal);
		}
		return animal;
	}
	
}
