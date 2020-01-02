package com.base.model.retrofit.response.songListResponse

/**
 * Json response ornegi
 *  {
        "data": [
        {
        "id": 1,
        "category": "Classical",
        "name": "HAYDN 'CONCERTO D-MAJOR'",
        "fileAddress": "http://www.hochmuth.com/mp3/Haydn_Cello_Concerto_D-1.mp3"
        },
        {
        "id": 2,
        "category": "Classical",
        "name": "TCHAIKOVSKY 'ROCOCO-VARIATIONS'",
        "fileAddress": "http://www.hochmuth.com/mp3/Tchaikovsky_Rococo_Var_orch.mp3"
        },
        {
        "id": 3,
        "category": "Classical",
        "name": "VIVALDI SONATA 'ADAGIO'",
        "fileAddress": "http://www.hochmuth.com/mp3/Vivaldi_Sonata_eminor_.mp3"
        },
        {
        "id": 4,
        "category": "Classical",
        "name": "TCHAIKOVSKY 'NOCTURNE'",
        "fileAddress": "http://www.hochmuth.com/mp3/Tchaikovsky_Nocturne__orch.mp3"
        },
        {
        "id": 5,
        "category": "Classical",
        "name": " HAYDN 'ADAGIO'",
        "fileAddress": "http://www.hochmuth.com/mp3/Haydn_Adagio.mp3"
        },
        {
        "id": 6,
        "category": "Classical",
        "name": "BOCCHERINI 'CONCERTO IN D'",
        "fileAddress": "http://www.hochmuth.com/mp3/Boccherini_Concerto_478-1.mp3"
        },
        {
        "id": 7,
        "category": "Classical",
        "name": "BLOCH 'PRAYER'",
        "fileAddress": "http://www.hochmuth.com/mp3/Bloch_Prayer.mp3"
        },
        {
        "id": 8,
        "category": "Classical",
        "name": "BEETHOVEN 'VARIATIONS'",
        "fileAddress": "http://www.hochmuth.com/mp3/Beethoven_12_Variation.mp3"
        }
        ],
        "errorMessage": "",
        "isSuccess": true,
        "statusCode": "400"
    }
 */
data class SongListResponse(
    val id: Int,
    val category: String,
    val fileAddress: String,
    val name: String
)