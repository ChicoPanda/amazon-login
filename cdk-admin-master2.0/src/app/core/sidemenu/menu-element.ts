export const menus = [

  {
    "name"   : "Dashboard",
    "icon"   : "dashboard",
    "link"   : false,
    "open"   : false,
    "chip"   :  { "value": 1,'color': 'accent'} ,
    "sub"    :  [
                    {
                        "name": "Dashboard",
                        "link": "/auth/dashboard",
                        "icon": "dashboard",
                        "chip"   : false,
                        "open"   : true,
                    }
                    
                ]
},
  {
    "name": "Tables",
    "icon": "list",
    "link": false,
    "open": false,
    "chip": { "value": 2, 'color': 'accent' },
    "sub": [

      {
        "name": "Users",
        "icon": "filter_center_focus",
        "link": "/auth/tables/users",
        "open": false,
      }
    ]

  }
]
