## CubesCocktail

An expandable plugin for Minecraft based on [Paper](https://papermc.io/) that acts as a framework for the customization of various aspects of your server. It is based on a powerful system that allows you to define actions, conditions, variables, loops just like in real programming.

Each aspect of the game can be customized installing the related addon, the current list of addons is:

- ⚔️ [ItemDrink](https://github.com/CubesCocktail/ItemDrink), to create custom items with special abilities
- 🧟 [MobDrink](https://github.com/CubesCocktail/MobDrink), to create custom mobs

### Building

In order to build your own copy of the plugin you need to build [JummesLibs](https://github.com/ZamponiMarco/JummesLibs) and publish it to the local Maven repository.

After building JummesLibs, you can simply build CubesCocktail using Maven, executing the Maven install task.

### Installation

To install the plugin simply drag and drop the associated jar file inside the plugins folder of your server and start it.

After the first start, a folder named addons will appear inside the plugin folder. Inside there you can insert all the addons you want to install. After a server reload they will be usable. Check out the addons Github pages to see how to download them.

### Documentation

The plugin is documented through the use of extensive tooltips that describe every configuration object. You can also find the documentation about the commands to use by typing the command /cc help.

To obtain help from the developer itself and the other users you can join the [Discord server](https://discord.gg/TzREkc9).