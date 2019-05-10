'use strict';

myapp.service('ProjectMemberService',['$http', function ($http) {
    this.getMember = function getMember(memberReadRequest){
    	return $http({
    		method: 'POST',
    		url: 'projects/project-detail/member-read',
    		data: memberReadRequest,
    		headers: 'Accept:application/json'
    	});
    }
}]);
