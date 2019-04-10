(function() {
    'use strict';
    angular
        .module('mmsnapWebApp')
        .factory('SelfRatedHealth', SelfRatedHealth);

    SelfRatedHealth.$inject = ['$resource', 'DateUtils'];

    function SelfRatedHealth ($resource, DateUtils) {
        var resourceUrl =  'api/self-rated-healths/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date = DateUtils.convertDateTimeFromServer(data.date);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
