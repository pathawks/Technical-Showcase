use std::fmt;
use structopt::StructOpt;

#[derive(StructOpt)]
struct Cli {
    album: Option<u64>,
}

impl fmt::Display for Cli {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        match self.album {
            Some(album_id) => write!(f, "album: {}", album_id),
            None => write!(f, "No album specified"),
        }
    }
}

fn main() {
    let args = Cli::from_args();
    println!("{}", args);
}
