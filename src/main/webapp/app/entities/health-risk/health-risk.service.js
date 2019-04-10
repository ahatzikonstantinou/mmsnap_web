(function() {
    'use strict';
    angular
        .module('mmsnapWebApp')
        .factory('HealthRisk', HealthRisk);

    HealthRisk.$inject = ['$resource', 'DateUtils'];

    function HealthRisk ($resource, DateUtils) {
        var resourceUrl =  'api/health-risks/:id';

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
