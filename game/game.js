window.onload = function() {

    /*var game = new Phaser.Game(800, 600, Phaser.AUTO, 'phaser-example', { preload: preload, create: create, update: update });

    function preload() {

        game.load.image('stars', 'assets/misc/starfield.jpg');
        game.load.image('ship', 'assets/sprites/thrust_ship2.png');
    }

    var ship;
    var starfield;
    var cursors;

    function create() {

        game.world.setBounds(0, 0, 1920, 1200);

        game.physics.startSystem(Phaser.Physics.P2JS);
        game.physics.p2.defaultRestitution = 0.8;

        starfield = game.add.tileSprite(0, 0, 800, 600, 'stars');
        starfield.fixedToCamera = true;

        ship = game.add.sprite(200, 200, 'ship');

        game.physics.p2.enable(ship);

        game.camera.follow(ship);

        cursors = game.input.keyboard.createCursorKeys();
    }

    function update() {

        if (cursors.left.isDown)
        {
            ship.body.thrustLeft(100);
        }
        else if (cursors.right.isDown)
        {
            ship.body.thrustRight(100);
        }

        if (cursors.up.isDown)
        {
            ship.body.thrust(400);
        }
        else if (cursors.down.isDown)
        {
            ship.body.reverse(400);
        }

        if (!game.camera.atLimit.x)
        {
            starfield.tilePosition.x -= (ship.body.velocity.x * game.time.physicsElapsed);
        }

        if (!game.camera.atLimit.y)
        {
            starfield.tilePosition.y -= (ship.body.velocity.y * game.time.physicsElapsed);
        }

    }

	*/// game definition, 320x480
	var game = new Phaser.Game(320, 480, Phaser.AUTO, "", {preload: preload, create: create, update: update});

    // the player
    var player;
    var cursors;

    var deltaT;
    var speedX = 0;
    var speedY = 0;

    // function executed on preload
	function preload() {
        game.load.image("player","game/player.png");	
        game.load.image("slope","game/slope.jpg");
	}

	// function to scale up the game to full screen
	function goFullScreen(){
        game.scale.pageAlignHorizontally = true;
        game.scale.pageAlignVertically = true;
        game.scale.scaleMode = Phaser.ScaleManager.SHOW_ALL;
        game.scale.setScreenSize(true);
	}

	// function to be called when the game has been created
	function create() {
        
        slope = game.add.tileSprite(0, 0, 320, 1280, 'slope');
        slope.fixedToCamera = true;

        game.world.setBounds(0, 0, 640, 3600);
        
        // initializing physics system
        //game.physics.startSystem(Phaser.Physics.ARCADE);
        
        game.physics.startSystem(Phaser.Physics.P2JS);
        game.physics.p2.defaultRestitution = 0.8;

        // going full screen
        goFullScreen();
        // adding the player on stage
        player = game.add.sprite(160,240,"player");
        // setting player anchor point
        player.anchor.setTo(0.5);
        // enabling physics car.body.collideWorldBounds = true;
        
        game.physics.enable(player, Phaser.Physics.ARCADE);
        game.camera.follow(player);

        // the player will collide with bounds
        player.body.collideWorldBounds = true;
        // setting player bounce
        player.body.bounce.set(0.2);

        cursors = game.input.keyboard.createCursorKeys();


        // setting gyroscope update frequency
        gyro.frequency = 10;
        // start gyroscope detection

        //var x = speedX;
        //var y = speedY;
        gyro.startTracking(function(o) {
            // updating player velocity
            
            //speedX += o.gamma/10;
            //speedY += o.beta/30; //vertical impact on speed is less then horizontal.

            player.body.velocity.x += o.gamma/10;
            player.body.velocity.y += o.beta/20;
        });
	}

    function update() {

        //console.log("update");
        //console.log("speedX: " + speedX);
        

        

        /*if (cursors.left.isDown)
        {
            speedX -= 5;
        }
        else if (cursors.right.isDown)
        {
            speedX += 5;
        }
        
        if (cursors.down.isDown)
        {
            speedY += 1;
        }
        else if (cursors.up.isDown) {
            speedY -= 1;
        }

        player.body.velocity.x = speedX;
        player.body.velocity.y = speedY;*/

        if (!game.camera.atLimit.x)
        {
            slope.tilePosition.x -= (player.body.velocity.x * game.time.physicsElapsed);
        }

        if (!game.camera.atLimit.y)
        {
            slope.tilePosition.y -= (player.body.velocity.y * game.time.physicsElapsed);
        }
    }
}