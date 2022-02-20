use std::fmt;
use structopt::StructOpt;

#[derive(StructOpt)]
struct Cli {
    album: Option<u64>, // TODO: This will fail if value is not an integer
}

impl fmt::Display for Cli {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        match self.album {
            Some(album_id) => write!(f, "album: {}", album_id),
            None => write!(f, "No album specified"),
        }
    }
}

fn create_url(album: Option<u64>) -> String {
    const BASE: &str = "https://jsonplaceholder.typicode.com/photos";
    return format!("{}", BASE);
}

fn main() {
    let args = Cli::from_args();
    println!("{}", create_url(args.album));
}
