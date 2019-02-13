# oraclechallenge
challenge from oracle


HOW TO RUN :

1 - Go to the "oraclechallenge" directory
 
2 - Execute the command :
"java -jar target/oracle-challenge-1.0-SNAPSHOT.jar siteMapExample.txt"

HIGH-LEVEL DESIGN
Controller
- Retrieves input parameters
Starts the simulation 
Instanciates the strategy
Instanciates the different services
The controller has no business, it is there to launch the simulation and instances the services and strategies that we want to use. If in the future we want to use a different strategy, we should simply instantiate the new strategy in the controller.

Strategy
- Uses services
- Instantiates the models
The strategy defines the application's behaviour for each instruction entered by the user. It is the strategy that decides which models it needs to instantiate and which service it needs to use. In my example, I have only one "BulldozerStrategy" strategy, and decides according to the instruction if the bulldozer spends fuel units.

Services
- Handles the different models
The services are there to manipulate the different models. They have all the skills concerning models. For example, BulldozerService allows you to add fuel units to the Bulldozer model.

Model
- Site
- Square
- Bulldozer
The models represent the different components that will be modified throughout the simulation. For example, the Bulldozer model represents the machine and contains information such as the units of diesel fuel spent.
