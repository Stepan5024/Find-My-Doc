import os
import shutil
import re



def split_json_images(json_path, image_folder_path, ranges):

    with open(json_path, encoding='utf-8') as infile:
        lines = infile.readlines()

    for i, (start, end) in enumerate(ranges):
        # Adjusting indices to be zero-based
        start_index = start
        end_index = end

        # Create output file name

        if i == 0:
            path = './train'
        if i == 1:
            path = './val'
        if i == 2:
            path = './test'

        output_file = f'{path}/labels.json'

        # Write the specified range of lines to the output file
        with open(output_file, 'w', encoding='utf-8') as outfile:
            if lines[start_index][0] != '{':
                outfile.write('{' + '\n')
            outfile.writelines(lines[start_index:end_index-1])
            if lines[end_index - 1] != '}':
                if lines[end_index - 1][-2] == ',':
                    outfile.write(lines[end_index - 1][:-2] + '\n')
                else:
                    outfile.write(lines[end_index - 1][:-1] + '\n')
                outfile.write('}')
            else:
                outfile.write('}')

        print(f'Written lines {start} to {end} to {output_file}')

        # Copy the specified range of files
        for i in range(start_index, end_index):

            img_filename = lines[i].split(': ')[0].replace("\"",'')

            if img_filename not in ['{\n', '}\n']:
                src_file = os.path.join(image_folder_path, img_filename)
                dest_file = os.path.join(f'{path}/images', img_filename)
              
                # Copy the file
                shutil.copy2(src_file, dest_file)
                print(f'Copied: {src_file} to {dest_file}')


if __name__ == "__main__":
    input_json_file = 'labels.json'
    images_path = './1'

    line_ranges = [
        (0, 1001),    
        (1001, 1251), 
        (1251, 1501)  
    ]

    split_json_images(input_json_file, images_path, line_ranges)
