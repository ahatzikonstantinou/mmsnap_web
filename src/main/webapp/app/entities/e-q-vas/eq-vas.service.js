(function() {
    'use strict';
    angular
        .module('mmsnapWebApp')
        .factory('EQVas', EQVas);

    EQVas.$inject = ['$resource', 'DateUtils'];

    function EQVas ($resource, DateUtils) {
        var resourceUrl =  'api/e-q-vas/:id';

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
