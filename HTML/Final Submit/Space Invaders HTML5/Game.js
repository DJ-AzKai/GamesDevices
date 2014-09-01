/**
 * Initialize the Game and starts it.
 */
 //credit to Steven Lambert for his blog tutorial on Game Structure.
 //credit to xXkaijuking91Xx for the star sprite
 //credit to sandbox.yoyogames.com for the ship sprite
var game = new Game();
var starArray = [];
var score = 0;
var misses = 0;
function init() {
	if(game.init())
		game.start();
}
/**
 * Creates the Drawable object which will be the base class for
 * all drawable objects in the game. Sets up default variables
 * that all child objects will inherit, as well as the default
 * functions.
 */
function Drawable() {
	this.init = function(x, y, width, height) {
		// Default variables
		// Defualt variables
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	this.speed = 0;
	this.canvasWidth = 0;
	this.canvasHeight = 0;
	// Define abstract function to be implemented in child objects
	this.draw = function() {
	};
}
function playBackgroundLoop()
{
	//works in fire fox and google
	var bgMusic = new Audio("Sounds/Background.wav");
	bgMusic.play();
	bgMusic.loop = true;
};
function playAbsorb()
{
	var absorb = new Audio("Sounds/absorb.wav");
	absorb.play();
}

/**
 * Define an object to hold all our images for the game so images
 * are only ever created once. This type of object is known as a 
 * singleton.
 */
var imageRepository = new function() {
	// Define images
	this.empty = null;
	this.star = new Image();
	this.background = new Image();
	this.spaceship = new Image();
	
	// Ensure all images have loaded before starting the game
	var numImages = 3;
	var numLoaded = 0;
	function imageLoaded() {
		numLoaded++;
		if (numLoaded === numImages) {
			window.init();
		}
	}
	this.background.onload = function() {
		imageLoaded();
	}
	this.spaceship.onload = function() {
		imageLoaded();
	}
	this.star.onload = function(){
		imageLoaded();
	}
	// Set images src
	this.background.src = "imgs/bg.png";
	this.spaceship.src = "imgs/SpaceShip.png";
	this.star.src = "imgs/Star.png";
	
}
//create Background Class
function Background() {
	this.speed = 1; // Redefine speed of the background for panning
	
	// Implement abstract function
	this.draw = function() {
		// Pan background
		this.x += this.speed;
		this.context.drawImage(imageRepository.background, this.x, this.y);
		
		// Draw another image at the top edge of the first image
		this.context.drawImage(imageRepository.background, this.x - this.canvasWidth, this.y);

		// If the image scrolled off the screen, reset
		if (this.x >= this.canvasWidth)
			this.x = 0;
	};
}
// Set Background to inherit properties from Drawable
Background.prototype = new Drawable();


// The keycodes that will be mapped when a user presses a button.
// Original code by Doug McInnes
KEY_CODES = {
  32: 'space',
  37: 'left',
  38: 'up',
  39: 'right',
  40: 'down',
}
// Creates the array to hold the KEY_CODES and sets all their values
// to false. Checking true/flase is the quickest way to check status
// of a key press and which one was pressed when determining
// when to move and which direction.
KEY_STATUS = {};
for (code in KEY_CODES) {
  KEY_STATUS[ KEY_CODES[ code ]] = false;
}
/**
 * Sets up the document to listen to onkeydown events (fired when
 * any key on the keyboard is pressed down). When a key is pressed,
 * it sets the appropriate direction to true to let us know which
 * key it was.
 */
document.onkeydown = function(e) {
  // Firefox and opera use charCode instead of keyCode to
  // return which key was pressed.
  var keyCode = (e.keyCode) ? e.keyCode : e.charCode;
  if (KEY_CODES[keyCode]) {
    e.preventDefault();
    KEY_STATUS[KEY_CODES[keyCode]] = true;
  }
}
/**
 * Sets up the document to listen to onkeyup events (fired when
 * any key on the keyboard is released). When a key is released,
 * it sets the appropriate direction to false to let us know which
 * key it was.
 */
document.onkeyup = function(e) {
  var keyCode = (e.keyCode) ? e.keyCode : e.charCode;
  if (KEY_CODES[keyCode]) {
    e.preventDefault();
    KEY_STATUS[KEY_CODES[keyCode]] = false;
  }
}

/**
 * Create the Ship object that the player controls. The ship is
 * drawn on the "ship" canvas and uses dirty rectangles to move
 * around the screen.
 */
function Ship() {
	this.speed = 4;
	var counter = 0;
	this.draw = function() {
		this.context.drawImage(imageRepository.spaceship, this.x, this.y);
	};
	this.move = function() {
		counter++;
		// Determine if the action is move action
		if (KEY_STATUS.down || KEY_STATUS.up) {
			// The ship moved, so erase it's current image so it can
			// be redrawn in it's new location
			this.context.clearRect(this.x, this.y, this.width, this.height);
			// Update x and y according to the direction to move and
			// redraw the ship. Change the else if's to if statements
			// to have diagonal movement.

			if (KEY_STATUS.up) {
				this.y -= this.speed;
				if ((this.y  + this.height) >= this.canvasHeight)
					this.y = this.canvasHeight - this.height;
			} 
			else if (KEY_STATUS.down) {
				this.y += this.speed;
				if (this.y <= 0)
					this.y = 0;
			}
			// Finish by redrawing the ship
			this.draw();
		}
	};
}
Ship.prototype = new Drawable();

//create our star object.
function Star(){
	this.speed = Math.floor(Math.random()*6);
	this.draw = function() {
		this.context.drawImage(imageRepository.star, this.x, this.y);
	};
	this.update = function(){
		//clear current area
		this.context.clearRect(this.x, this.y, this.width, this.height);
		//move the star
		if(this.x + this.width >=600)
		{
			this.x -= 1000;
			this.y = Math.floor(Math.random()*340);
			this.x -= Math.floor(Math.random()*200); //just to space them out a bit
			misses++;
		}
		this.x += this.speed;
		//redraw the star.
		this.draw();
	}
	this.reset= function(){
		this.context.clearRect(this.x, this.y, this.width, this.height);
		this.x -= 1000;
		this.x -= Math.floor(Math.random()*200); //just to space them out a bit
		this.y = Math.floor(Math.random()*340);
	}
}
Star.prototype = new Drawable();
 /**
 * Creates the Game object which will hold all objects and data for
 * the game.
 */
function Game() {
	/*
	 * Gets canvas information and context and sets up all game
	 * objects.
	 * Returns true if the canvas is supported and false if it
	 * is not. This is to stop the animation script from constantly
	 * running on browsers that do not support the canvas.
	 */
	this.init = function() {
		// Get the canvas elements
		this.bgCanvas = document.getElementById('background');
		this.shipCanvas = document.getElementById('ship');
		this.mainCanvas = document.getElementById('main');
		this.TextCanvas = document.getElementById('text');
		
		// Test to see if canvas is supported. Only need to
		// check one canvas
		//draw the score to make sure it's working.
		if (this.bgCanvas.getContext) {
			this.bgContext = this.bgCanvas.getContext('2d');
			this.shipContext = this.shipCanvas.getContext('2d');
			this.mainContext = this.mainCanvas.getContext('2d');
			this.TextContext = this.TextCanvas.getContext('2d');
			// Initialize objects to contain their context and canvas
			// information
			Background.prototype.context = this.bgContext;
			Background.prototype.canvasWidth = this.bgCanvas.width;
			Background.prototype.canvasHeight = this.bgCanvas.height;
			Ship.prototype.context = this.shipContext;
			Ship.prototype.canvasWidth = this.shipCanvas.width;
			Ship.prototype.canvasHeight = this.shipCanvas.height;
			Star.prototype.context = this.shipContext;
			Star.prototype.canvasWidth = this.shipCanvas.width;
			Star.prototype.canvasHeight = this.shipCanvas.height;
			// Initialize the background object
			this.background = new Background();
			this.background.init(0,0); // Set draw point to 0,0
			// Initialize the ship object
			this.ship = new Ship();
			//play our audio
			playBackgroundLoop();
			//draw our score for the first time.
			this.drawScore();
			// Set the ship to start near the bottom middle of the canvas
			var shipStartX = 510;
			var shipStartY = (360 / (4*3)) + 150;
			
			for(var i = 0; i < 20; ++i)
			{
				starArray.push(new Star());
				                     //send each star 500 pixels behind the previous one
				starArray[i].init((((i+1) * (0-1) ) * 500), Math.floor(Math.random()*340), 20,20);
				
			}
			this.ship.init(shipStartX, shipStartY, 90,
			               100);
						  
			return true;
		} 
		else 
		{
			return false;
		}
	};
	this.updateScore = function()
	{
		score += 10;
		//this is where we're going to the score.
		if(score >= 200)
		{
			window.close();
		}
		this.drawScore();
	}
	this.drawMisses= function()
	{
		this.TextContext.clearRect(200,0, 200,50);
		this.TextContext.font = "20px Verdana";
		this.TextContext.fillStyle= "#00ff80";
		this.TextContext.fillText("Misses: " + misses + " / 5", 200,20);
	}
	this.drawScore = function()
	{
		this.TextContext.clearRect(0,0, 200,50); //clear our small canvas.
		this.TextContext.font="20px Verdana";
		// Create gradient
		var gradient=this.TextContext.createLinearGradient(0,0,this.TextCanvas.width,0);
		gradient.addColorStop("0","magenta");
		gradient.addColorStop("0.5","blue");
		gradient.addColorStop("1.0","red");
		// Fill with gradient
		this.TextContext.fillStyle=gradient;
		this.TextContext.fillText("Score: "+ (score/10) + " / 20",0,20);
	}
	// Start the animation loop
	this.start = function() {
		this.ship.draw();
		animate();
	};
}


/**
 * The animation loop. Calls the requestAnimationFrame shim to
 * optimize the game loop and draws all game objects. This
 * function must be a gobal function and cannot be within an
 * object.
 */

function animate() {
	requestAnimFrame( animate );
	game.background.draw();
	game.ship.move();
	game.drawMisses();
	if(misses > 5)
	{
		window.close();
	}
	
	for(var k = 0; k < 20; k++){
		starArray[k].update();
		if(checkCollision(game.ship, starArray[k]))
		{
			playAbsorb();
			game.updateScore(10);
			starArray[k].reset();
		}
	}
}

function checkCollision(object1, object2)
{
	//Bounding Box Collision Detection.
	if (object1.x < object2.x + object2.width  && object1.x + object1.width  > object2.x &&
		object1.y < object2.y + object2.height && object1.y + object1.height > object2.y) {
		return true;
	}
}
/**	
 * requestAnim shim layer by Paul Irish
 * Finds the first API that works to optimize the animation loop, 
 * otherwise defaults to setTimeout().
 */
window.requestAnimFrame = (function(){
	return  window.requestAnimationFrame       || 
			window.webkitRequestAnimationFrame || 
			window.mozRequestAnimationFrame    || 
			window.oRequestAnimationFrame      || 
			window.msRequestAnimationFrame     || 
			function(/* function */ callback, /* DOMElement */ element){
				window.setTimeout(callback, 1000 / 60);
			};
})();