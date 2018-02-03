package ng.com.blogspot.httpofficialceo.sharecon.adapter;

/**
 * Created by official on 12/26/17.
 */


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.util.ArrayList;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import ng.com.blogspot.httpofficialceo.sharecon.BarCodeActivity;
import ng.com.blogspot.httpofficialceo.sharecon.R;
import ng.com.blogspot.httpofficialceo.sharecon.helper.CircleTransform;
import ng.com.blogspot.httpofficialceo.sharecon.model.Contacts;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private static int currentSelectedIndex = -1;
    public List<Contacts> cont;
    Contacts contacts;
    boolean checked = false;
    // private Toast myToast;
    // ContactAdapterListener listener;
    View vv;
    private LayoutInflater layoutInflater;
    private Context mContext;
    private ArrayList<Contacts> arraylist;
    private SparseBooleanArray selectedItems;
    private SparseBooleanArray animationItemsIndex;
    private boolean reverseAllAnimations = false;


    public ContactsAdapter(LayoutInflater inflater, List<Contacts> items) {
        this.layoutInflater = inflater;
        this.cont = items;
        // this.listener = listener;
        this.arraylist = new ArrayList<Contacts>();
        this.arraylist.addAll(cont);
        selectedItems = new SparseBooleanArray();
        animationItemsIndex = new SparseBooleanArray();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.contactlist_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Contacts contacts = cont.get(position);
        String name = (contacts.getName());

        holder.title.setText(name);
        holder.phone.setText(contacts.getPhone());
        holder.iconText.setText(contacts.getName().substring(0, 1));
        holder.itemView.setActivated(selectedItems.get(position, false));


        applyProflePicture(holder, contacts);


    }

    private void applyProflePicture(ViewHolder holder, Contacts contacts) {
        if (!TextUtils.isEmpty(contacts.getPicture())) {
            Glide.with(mContext).load(contacts.getPicture())
                    .thumbnail(0.5f)
                    .crossFade()
                    .transform(new CircleTransform(mContext))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.profileImage);

            holder.profileImage.setColorFilter(null);
            holder.iconText.setVisibility(View.GONE);
        } else {
            holder.profileImage.setImageResource(R.drawable.bg_circle);
            // holder.profileImage.setColorFilter(contacts.getColor());
            holder.iconText.setVisibility(View.VISIBLE);
        }
    }




    @Override
    public int getItemCount() {
        return cont.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public TextView title, iconText;
        public TextView phone;
        public CircleImageView profileImage;
        public RelativeLayout iconContainer, iconBack, iconFront;
        public LinearLayout contact_select_layout;
        public LinearLayout contactContainer;
        public CheckBox contactsCheckBox;
        private CardView contactCard;
        private ImageView sendAction;


        public ViewHolder(final View itemView) {
            super(itemView);
            // this.setIsRecyclable(false);
            title = (TextView) itemView.findViewById(R.id.name);
            phone = (TextView) itemView.findViewById(R.id.no);
            profileImage = (CircleImageView) itemView.findViewById(R.id.icon_profile);
            iconText = (TextView) itemView.findViewById(R.id.icon_text);
            iconFront = (RelativeLayout) itemView.findViewById(R.id.icon_front);
            sendAction = (ImageView) itemView.findViewById(R.id.sendText);
            iconContainer = (RelativeLayout) itemView.findViewById(R.id.icon_container);
            contactContainer = (LinearLayout) itemView.findViewById(R.id.message_container);
            contactsCheckBox = (CheckBox) itemView.findViewById(R.id.contacts_checkbox);
            itemView.setOnLongClickListener(this);

            iconContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            sendAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent myIntent = new Intent(view.getContext(), BarCodeActivity.class);
                    myIntent.putExtra("CONTACT_NAME", cont.get(getAdapterPosition()).getName());
                    myIntent.putExtra("CONTACT_NUMBER", cont.get(getAdapterPosition()).getPhone());
                    view.getContext().startActivity(myIntent);

                }
            });

            contactContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
                    alertDialog.setTitle("DIALER");
                    alertDialog.setMessage("Permission to proceed calling " + cont.get(getAdapterPosition()).getName().toUpperCase() + " ?");
                    alertDialog.setIcon(R.drawable.ic_phone_in_talk_black_24dp);

                    alertDialog.setPositiveButton("PROCEED", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            String number = "tel:" + cont.get(getAdapterPosition()).getPhone();
                            Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(number));
                            view.getContext().startActivity(callIntent);

                        }
                    });

                    alertDialog.setNegativeButton("RETURN", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toasty.info(view.getContext(), "Action aborted", Toast.LENGTH_SHORT, true).show();
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();

                }
            });



            contactContainer.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Toast.makeText(view.getContext(), "Message container longclicked at " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                    return true;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
                    alertDialog.setTitle("DIALER");
                    alertDialog.setMessage("Permission to proceed calling " + cont.get(getAdapterPosition()).getName().toUpperCase() + " ?");
                    alertDialog.setIcon(R.drawable.ic_phone_in_talk_black_24dp);

                    alertDialog.setPositiveButton("PROCEED", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            String number = "tel:" + cont.get(getAdapterPosition()).getPhone();
                            Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(number));
                            v.getContext().startActivity(callIntent);

                        }
                    });

                    alertDialog.setNegativeButton("RETURN", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toasty.info(v.getContext(), "Action aborted", Toast.LENGTH_SHORT, true).show();
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();

                }
            });


        }

        @Override
        public boolean onLongClick(View v) {
            Toast.makeText(v.getContext(), "background longclick " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
            contactsCheckBox.setVisibility(View.VISIBLE);
            contactsCheckBox.setChecked(true);
            sendAction.setVisibility(View.GONE);
            // listener.onRowLongClicked(getAdapterPosition());
            itemView.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            return true;
        }
    }




}