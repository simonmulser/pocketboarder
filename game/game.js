window.onload = function() {

    /*var audioJSON = {
        spritemap: {
        'aua1': {
            start: 1,
            end: 2,
            loop: false
        },
        'aua2': {
            start: 3,
            end: 3.5,
            loop: false
        },
        'aua3': {
            start: 4,
            end: 7.2,
            loop: false
        }
    };*/
   
    // game definition, 320x480
	var game = new Phaser.Game(320, 480, Phaser.AUTO, "", {preload: preload, create: create, update: update});

    // the player
    var player;
    var cursors;

    var deltaT;
    var speedX = 0;
    var speedY = 0;

    var timeDisplay = document.getElementById("time");

    // function executed on preload
	function preload() {
        game.load.image("player","game/boarder_left.png");	
        game.load.image("slope","game/slope.jpg");

        game.load.spritesheet("boarder", "game/boarder.png", 92, 98, 2);
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
        
        //game.load.audiosprite('sfx', 'audio/fx_mixdown.ogg', null, audioJSON);

        slope = game.add.tileSprite(0, 0, 600, 3900, 'slope');
        slope.fixedToCamera = true;

        game.world.setBounds(0, 0, 600, 3900);
        
        // initializing physics system
        game.physics.startSystem(Phaser.Physics.ARCADE);
        
        // going full screen
        goFullScreen();
        // adding the player on stage
        player = game.add.sprite(92, 98, "boarder");
        player.frame = 2;

        // setting player anchor point
        player.anchor.setTo(0.5);
        // enabling physics car.body.collideWorldBounds = true;
        
        game.physics.enable(player, Phaser.Physics.ARCADE);
        game.camera.follow(player);

        // the player will collide with bounds
        player.body.collideWorldBounds = true;
        // setting player bounce
        player.body.bounce.set(0.2);

        player.body.onCollide = new Phaser.Signal();
        player.body.onCollide.add(onCollission, this);

        cursors = game.input.keyboard.createCursorKeys();


        // setting gyroscope update frequency
        gyro.frequency = 10;
        // start gyroscope detection

        //var x = speedX;
        //var y = speedY;
        gyro.startTracking(function(o) {
            // updating player velocity
            
            speedX += o.gamma/10;
            speedY += o.beta/50; //vertical impact on speed is less then horizontal.

            player.body.velocity.x += o.gamma/10;
            player.body.velocity.y += o.beta/50;

            if (player.body.velocity.y < 0)
                player.body.velocity.y = 0;
        });
	}

    function onCollission() {
        return null;
    }

    function update() {

        if (player.body.velocity.x > 0)
            player.frame = 2;
        else
            player.frame = 1;

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

        if (player.y > 3800) {
            timeDisplay.textContent = "FINISH";

            window.extAsyncInit = function() {
				MessengerExtensions.requestCloseBrowser(function success() {
					console.log("test-success")

					var http = new XMLHttpRequest();
					var url = "get_data.php";
					var params = {data: "data"};
					http.open("POST", "https://ff3778bb.ngrok.io/webhook", true);

					//Send the proper header information along with the request
					http.setRequestHeader("Content-type", "application/json");

					http.onreadystatechange = function() {//Call a function when the state changes.
					    if(http.readyState == 4 && http.status == 200) {
					        alert(http.responseText);
					    }
					}
					http.send(JSON.stringify(data));

				}, function error(err) {
					console.log("test-err")
				});
			}
        }
        else
            timeDisplay.textContent = formatTime(this.game.time.totalElapsedSeconds());

        if (!game.camera.atLimit.x)
        {
            slope.tilePosition.x -= (player.body.velocity.x * game.time.physicsElapsed);
        }

        if (!game.camera.atLimit.y)
        {
            slope.tilePosition.y -= (player.body.velocity.y * game.time.physicsElapsed);
        }
    }

    function formatTime (str) {
        return str.toFixed(2).replace(".",":");
    }
}