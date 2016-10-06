//
//  TTFirstViewController.m
//  TF2 Finance
//
//  Created by Evan Langlais on 5/17/14.
//  Copyright (c) 2014 Treehouse Technologies. All rights reserved.
//

#import "TTFirstViewController.h"

@interface TTFirstViewController ()

@end

@implementation TTFirstViewController

@synthesize budkey, budkeydate, budusd, budusddate, budgraph, budspinner, budtitle;

- (void)viewDidLoad
{
    [super viewDidLoad];
    
	// Do any additional setup after loading the view, typically from a nib.
    [self doLoad];
}

-(void)showAd
{

    
    if ( [UIDevice currentDevice].userInterfaceIdiom == UIUserInterfaceIdiomPad ) {
        /* do something specifically for iPad. */
        bannerView_ = [[GADBannerView alloc] initWithAdSize:kGADAdSizeSmartBannerPortrait];
        bannerView_.adUnitID = @"ca-app-pub-8629268905695480/8365988192";
        bannerView_.rootViewController = self;
        
        [self.view addSubview:bannerView_];
        [bannerView_ loadRequest:[GADRequest request]];
    } else {
        /* do something specifically for iPhone or iPod touch. */
        bannerView_ = [[GADBannerView alloc] initWithAdSize:kGADAdSizeBanner];
        bannerView_.adUnitID = @"ca-app-pub-8629268905695480/8365988192";
        bannerView_.rootViewController = self;
        
        [self.view addSubview:bannerView_];
        [bannerView_ loadRequest:[GADRequest request]];
    }
    
    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)doLoad{
    
    [self isLoading:true];
    [self setFonts];
    
    [self downloadImageWithURL:[NSURL URLWithString:@"http://tf2finance.com/live.png"] completionBlock:^(BOOL succeeded, UIImage *image) {
        if (succeeded) {
            // change the image in the cell
            budgraph.image = image;
        }
        
        [self startData];
        
    }];
    
    
    
    
}

- (void)startData{
    
    NSURL *myURL = [NSURL URLWithString:@"http://treehousetech.net/dev/tf2financeprices.php"];
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:myURL cachePolicy:NSURLRequestReloadIgnoringLocalCacheData timeoutInterval:60];
    
    [[NSURLConnection alloc] initWithRequest:request delegate:self];
}

- (void)finishData:(NSString*) res{
    
    NSArray* tmp = [res componentsSeparatedByString:@"!"];
    NSString* varPrice = [[[tmp objectAtIndex:0] componentsSeparatedByString:@":"] objectAtIndex:0];
    NSString* varDate = [[[tmp objectAtIndex:1] componentsSeparatedByString:@":"] objectAtIndex:0];
    NSString* usdPrice = [[[tmp objectAtIndex:0] componentsSeparatedByString:@":"] objectAtIndex:1];
    NSString* usdDate = [[[[tmp objectAtIndex:1] componentsSeparatedByString:@":"] objectAtIndex:1] stringByTrimmingCharactersInSet: [NSCharacterSet whitespaceCharacterSet]];
    
    budkey.text = [[@"1 Bud = " stringByAppendingString:varPrice] stringByAppendingString:@" Keys"];
    budkeydate.text = varDate;
    budusd.text = [[@"1 Bud = " stringByAppendingString:usdPrice] stringByAppendingString:@" USD"];
    budusddate.text = usdDate;
    
    [self sizeLabel:budkey toRect:budkey.frame];
    [self sizeLabel:budkeydate toRect:budkeydate.frame];
    [self sizeLabel:budusd toRect:budusd.frame];
    [self sizeLabel:budusddate toRect:budusddate.frame];

    [self showAd];
    
    [self isLoading:false];
    
}

-(void)showDia:(NSString *)title Message:(NSString *)mes{
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:title
                                                    message:mes
                                                   delegate:nil
                                          cancelButtonTitle:@"It's Ok"
                                          otherButtonTitles:nil];
    [alert show];
}

- (void)downloadImageWithURL:(NSURL *)url completionBlock:(void (^)(BOOL succeeded, UIImage *image))completionBlock
{
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:url];
    [NSURLConnection sendAsynchronousRequest:request
                                       queue:[NSOperationQueue mainQueue]
                           completionHandler:^(NSURLResponse *response, NSData *data, NSError *error) {
                               if ( !error )
                               {
                                   UIImage *image = [[UIImage alloc] initWithData:data];
                                   completionBlock(YES,image);
                               } else{
                                   completionBlock(NO,nil);
                               }
                           }];
}

- (void)connection:(NSURLConnection *)connection didReceiveResponse:(NSURLResponse *)response
{
    responseData = [[NSMutableData alloc] init];
}

- (void)connection:(NSURLConnection *)connection didReceiveData:(NSData *)data
{
    [responseData appendData:data];
}

- (void)connection:(NSURLConnection *)connection didFailWithError:(NSError *)error
{
    budkey.text = @"Unable to Connect";
    budkeydate.text = @"Unable to Connect";
    budusd.text = @"Unable to Connect";
    budusddate.text = @"Unable to Connect";
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Unable to Connect"
                                                    message:@"We were unable to connect, sorry 'bout that"
                                                   delegate:nil
                                          cancelButtonTitle:@"It's Ok"
                                          otherButtonTitles:nil];
    [alert show];
}

- (void)connectionDidFinishLoading:(NSURLConnection *)connection
{
    NSString* txt = [[NSString alloc] initWithData:responseData encoding: NSASCIIStringEncoding];
    /*UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"DBG"
                                                    message:txt
                                                   delegate:nil
                                          cancelButtonTitle:@"OK"
                                          otherButtonTitles:nil];
    [alert show];*/
    [self finishData:txt];
}

- (void)isLoading:(BOOL) is{
    if(is){
        budspinner.alpha = 1.0;
        [budspinner startAnimating];
        budkey.text = @"Loading...";
        budkeydate.text = @"Loading...";
        budusd.text = @"Loading...";
        budusddate.text = @"Loading...";
        budgraph.image = [UIImage imageNamed:@"beta_icon-web.png"];
    } else {
        budspinner.alpha = 0.0;
        [budspinner stopAnimating];
    }
}

- (void) sizeLabel: (UILabel *) label toRect: (CGRect) labelRect  {
    
    // Set the frame of the label to the targeted rectangle
    label.frame = labelRect;
    
    // Try all font sizes from largest to smallest font size
    int fontSize = 300;
    int minFontSize = 5;
    
    // Fit label width wize
    CGSize constraintSize = CGSizeMake(label.frame.size.width, MAXFLOAT);
    
    do {
        // Set current font size
        label.font = [UIFont fontWithName:label.font.fontName size:fontSize];
        
        // Find label size for current font size
        CGRect textRect = [[label text] boundingRectWithSize:constraintSize
                                                       options:NSStringDrawingUsesLineFragmentOrigin
                                                    attributes:@{NSFontAttributeName:label.font}
                                                       context:nil];
        
        CGSize labelSize = textRect.size;
        
        // Done, if created label is within target size
        if( labelSize.height <= label.frame.size.height )
            break;
        
        // Decrease the font size and try again
        fontSize -= 2;
        
    } while (fontSize > minFontSize);
}

- (void)loadFont{ // Get the path to our custom font and create a data provider.
    
    NSString *fontPath = [[NSBundle mainBundle] pathForResource:@"TF2" ofType:@"ttf"];
    
    CGDataProviderRef fontDataProvider = CGDataProviderCreateWithFilename([fontPath UTF8String]);
    
    // Create the font with the data provider, then release the data provider. customFont =
    
    CGFontCreateWithDataProvider(fontDataProvider); CGDataProviderRelease(fontDataProvider);
    
}

- (void)setFonts{
    [self loadFont];
    budkey.font = [UIFont fontWithName:@"TF2" size:26];
    budkeydate.font = [UIFont fontWithName:@"TF2" size:26];
    budusd.font = [UIFont fontWithName:@"TF2" size:26];
    budusddate.font = [UIFont fontWithName:@"TF2" size:26];
    budtitle.font = [UIFont fontWithName:@"TF2" size:36];
}

@end
