'use strict';

angular.module('springChat.controllers', ['toaster'])
	.controller('ChatController', ['$scope', 'toaster', 'ChatSocket', function($scope, toaster, chatSocket) {
		  
		$scope.username     = '';
		$scope.sensordata 	= [];
		$scope.messages     = [];
		$scope.newMessage   = ''; 
		$scope.msgCount 	= 0;
		  
		$scope.sendMessage = function() {
			var destination = "/app/chat.message.out";
			chatSocket.send(destination, {}, $scope.username + ": " + $scope.newMessage);
			$scope.newMessage = '';
		};
			
		chatSocket.init('/ws');
		chatSocket.connect(function(frame) {

			chatSocket.subscribe("/topic/chat.sensordata", function(message) {
				$scope.sensordata.unshift(message.body);
			});
	        	 
			chatSocket.subscribe("/topic/chat.message", function(message) {
				$scope.msgCount++;
				$scope.messages.unshift("(" + $scope.msgCount + ") " + message.body);
	        });
	          
		}, function(error) {
			toaster.pop('error', 'Error', 'Connection error ' + error);
	    });
	}]);