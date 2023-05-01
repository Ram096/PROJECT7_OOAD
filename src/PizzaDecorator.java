//decorator class
public abstract class PizzaDecorator implements PizzaDec {
    public Pizza decoratedPizza;
    public PizzaDecorator(Pizza decoratedPizza){
        this.decoratedPizza = decoratedPizza;
    }
    public double getPrice(){
        return decoratedPizza.total;
    }

}
//Concrete Decorator
class MeatToppings extends PizzaDecorator{
    public MeatToppings(Pizza decoratedPizza){
        super(decoratedPizza);
    }

    public double getPrice(){
        return decoratedPizza.total * 1.2; //20% price increase to pizza
    }
}

//Concrete Decorator
class ExtraCheese extends PizzaDecorator{
    public ExtraCheese(Pizza decoratedPizza){
        super(decoratedPizza);
    }

    public double getPrice(){
        return decoratedPizza.total * 1.1; //10% price increase to pizza
    }

}
//Concrete decorator
class ExtraSauce extends PizzaDecorator{
    public ExtraSauce(Pizza decoratedPizza){
        super(decoratedPizza);
    }

    public double getPrice(){
        return decoratedPizza.total * 1.02; //2% price increase to pizza
    }
}
//Last concrete decorator
class FastOrder extends PizzaDecorator{
    public FastOrder(Pizza decoratedPizza){
        super(decoratedPizza);
    }
    public double getPrice(){
        return decoratedPizza.total * 1.5; //50% price increase to pizza
    }
}


