package linearization

 class Animal {
    
    def test = println("Animal")
  }
  
  trait Furry extends Animal {
    
    override def test = {
      println("Furry")
      super.test
    }
  }
  
  trait HasLegs extends Animal {
    
    override def test = {
      println("HasLegs")
      super.test
    }
  }
  
  trait FourLegged extends HasLegs {
    
    override def test = {
      println("FourLegged")
      super.test
    }
  }
  
  class Cat extends Animal with Furry with FourLegged {
    
  }
