(function() {
    'use strict';
    angular
        .module('mmsnapWebApp')
        .factory('IntentionsAndPlans', IntentionsAndPlans);

    IntentionsAndPlans.$inject = ['$resource', 'DateUtils'];

    function IntentionsAndPlans ($resource, DateUtils) {
        var resourceUrl =  'api/intentions-and-plans/:id';

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
