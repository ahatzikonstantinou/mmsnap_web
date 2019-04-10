(function() {
    'use strict';
    angular
        .module('mmsnapWebApp')
        .factory('WeeklyEvaluation', WeeklyEvaluation);

    WeeklyEvaluation.$inject = ['$resource', 'DateUtils'];

    function WeeklyEvaluation ($resource, DateUtils) {
        var resourceUrl =  'api/weekly-evaluations/:id';

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
