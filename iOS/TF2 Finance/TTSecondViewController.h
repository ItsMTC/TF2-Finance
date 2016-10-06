//
//  TTSecondViewController.h
//  TF2 Finance
//
//  Created by Evan Langlais on 5/17/14.
//  Copyright (c) 2014 Treehouse Technologies. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "GADBannerView.h"

@interface TTSecondViewController : UIViewController {
    GADBannerView *bannerView_;
    NSMutableData *responseData;
}

@property (nonatomic, retain) IBOutlet UILabel *keyref;
@property (nonatomic, retain) IBOutlet UILabel *keyusd;
@property (nonatomic, retain) IBOutlet UILabel *keyrefdate;
@property (nonatomic, retain) IBOutlet UILabel *keyusddate;
@property (nonatomic, retain) IBOutlet UILabel *keytitle;
@property (nonatomic, retain) IBOutlet UIImageView *keygraph;
@property (nonatomic, retain) IBOutlet UIActivityIndicatorView *keyspinner;

@end
