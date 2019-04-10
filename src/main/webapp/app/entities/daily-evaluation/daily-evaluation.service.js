(function() {
    'use strict';
    angular
        .module('mmsnapWebApp')
        .factory('DailyEvaluation', DailyEvaluation);

    DailyEvaluation.$inject = ['$resource', 'DateUtils'];

    function DailyEvaluation ($resource, DateUtils) {
        var resourceUrl =  'api/daily-evaluations/:id';

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
