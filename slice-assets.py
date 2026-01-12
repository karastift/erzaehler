import os
from matplotlib.image import imread, imsave

ASSETS_DIR = "assets"
PRESLICED_DIR = "presliced_flattened"

def normalize_name(name):
  return name.lower().replace(" ", "_")

def main(flatten_output=False):
  if not os.path.exists(PRESLICED_DIR):
    os.makedirs(PRESLICED_DIR)

  for asset_folder in os.listdir(ASSETS_DIR):

    asset_folder_path = os.path.join(ASSETS_DIR, asset_folder)

    if not os.path.isdir(asset_folder_path):
      continue

    expected_filename = normalize_name(asset_folder) + ".png"
    expected_file_path = os.path.join(asset_folder_path, expected_filename)

    if not os.path.isfile(expected_file_path):
      print(f"Skipping {asset_folder}: {expected_filename} not found.")
      continue

    im = imread(expected_file_path)

    # 8 rows with 4 columns
    M = im.shape[0] // 8
    N = im.shape[1] // 4

    # Only first row (y from 0 to 3)
    # Other tiles are ignored for now
    tiles = [im[0:M, y*N:(y+1)*N] for y in range(4)]

    if flatten_output:
      out_dir = PRESLICED_DIR
      os.makedirs(out_dir, exist_ok=True)
      for idx, tile in enumerate(tiles, 1):
        out_path = os.path.join(
          out_dir,
          f"{normalize_name(asset_folder)}_idling_{idx}.png"
        )
        imsave(fname=out_path, arr=tile, format="png")
    else:
      # Prepare output directory
      out_dir = os.path.join(PRESLICED_DIR, normalize_name(asset_folder))
      os.makedirs(out_dir, exist_ok=True)

      for idx, tile in enumerate(tiles, 1):
        out_path = os.path.join(out_dir, f"frame{idx}.png")
        imsave(fname=out_path, arr=tile, format="png")

    print(f"Processed {asset_folder}")

if __name__ == "__main__":
  # Set flatten_output=True to flatten all output into one folder with special naming
  main(flatten_output=True)