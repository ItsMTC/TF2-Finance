//
//  TTFirstViewController.h
//  TF2 Finance
//
//  Created by Evan Langlais on 5/17/14.
//  Copyright (c) 2014 Treehouse Technologies. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "GADBannerView.h"

@interface TTFirstViewController : UIViewController {
    GADBannerView *bannerView_;
    NSMutableData *responseData;
}


@property (nonatomic, retain) IBOutlet UILabel *budkey;
@property (nonatomic, retain) IBOutlet UILabel *budusd;
@property (nonatomic, retain) IBOutlet UILabel *budkeydate;
@property (nonatomic, retain) IBOutlet UILabel *budusddate;
@property (nonatomic, retain) IBOutlet UILabel *budtitle;
@property (nonatomic, retain) IBOutlet UIImageView *budgraph;
@property (nonatomic, retain) IBOutlet UIActivityIndicatorView *budspinner;



@end
